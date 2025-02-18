package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.UsuarioNoEncontradoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.UsuarioYaRegistradoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.DatosUsuarioInvalidosException;
import com.cultodeportivo.proyectofinal.model.Usuario;
import com.cultodeportivo.proyectofinal.repository.UsuarioRepository;
import com.cultodeportivo.proyectofinal.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;



    // Patrón para validar correo electrónico
    private static final Pattern PATRON_CORREO = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    // Patrón para validar cédula
    private static final Pattern PATRON_CEDULA = Pattern.compile("^\\d{10}$");

    public UsuarioServiceImp(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        validarDatosUsuario(usuario);
        verificarDuplicado(usuario.getPersona().getCedula(), usuario.getPersona().getCorreo());

        usuario.setId(null);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public Optional<Usuario> update(Usuario usuario) {
        return usuarioRepository.findById(usuario.getId())
                .map(existingUsuario -> {
                    validarDatosUsuario(usuario);

                    if (!existingUsuario.getPersona().getCedula().equals(usuario.getPersona().getCedula()) ||
                            !existingUsuario.getPersona().getCorreo().equals(usuario.getPersona().getCorreo())) {
                        verificarDuplicado(usuario.getPersona().getCedula(), usuario.getPersona().getCorreo());
                    }

                    existingUsuario.setFechaRegistro(usuario.getFechaRegistro());
                    existingUsuario.setPersona(usuario.getPersona());
                    return Optional.of(usuarioRepository.save(existingUsuario));
                }).orElseThrow(() -> new UsuarioNoEncontradoException("No se puede actualizar: Usuario con ID " + usuario.getId() + " no encontrado"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException("No se puede eliminar: Usuario con ID " + id + " no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> findByPersona_Correo(String personaCorreo) {
        return usuarioRepository.findByPersona_Correo(personaCorreo);
    }

    private void validarDatosUsuario(Usuario usuario) {
        if (usuario.getPersona() == null ||
                usuario.getPersona().getNombre() == null || usuario.getPersona().getNombre().isBlank() ||
                usuario.getPersona().getCedula() == null || !PATRON_CEDULA.matcher(usuario.getPersona().getCedula()).matches() ||
                usuario.getPersona().getCorreo() == null || !PATRON_CORREO.matcher(usuario.getPersona().getCorreo()).matches()) {
            throw new DatosUsuarioInvalidosException("Datos inválidos: Asegúrese de ingresar un nombre, cédula válida (10 dígitos) y un correo válido.");
        }
    }

    private void verificarDuplicado(String cedula, String correo) {
        if (usuarioRepository.existsByPersona_Cedula(cedula)) {
            throw new UsuarioYaRegistradoException("Ya existe un usuario con la cédula: " + cedula);
        }
        if (usuarioRepository.existsByPersona_Correo(correo)) {
            throw new UsuarioYaRegistradoException("Ya existe un usuario con el correo: " + correo);
        }
    }
}
