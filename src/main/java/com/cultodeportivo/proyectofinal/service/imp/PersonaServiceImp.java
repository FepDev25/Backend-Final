package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.PersonaNoEncontradaException;
import com.cultodeportivo.proyectofinal.exception.exceptions.PersonaYaRegistradaException;
import com.cultodeportivo.proyectofinal.exception.exceptions.DatosPersonaInvalidosException;
import com.cultodeportivo.proyectofinal.model.Persona;
import com.cultodeportivo.proyectofinal.repository.PersonaRepository;
import com.cultodeportivo.proyectofinal.service.PersonaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PersonaServiceImp implements PersonaService {

    private final PersonaRepository personaRepository;

    // Patrón para validar correo electrónico
    private static final Pattern PATRON_CORREO = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    // Patrón para validar cédula (ejemplo: solo números, longitud específica)
    private static final Pattern PATRON_CEDULA = Pattern.compile("^\\d{10}$");

    public PersonaServiceImp(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Override
    public Optional<Persona> findById(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    public Optional<Persona> findByCedula(String cedula) {
        return personaRepository.findByCedula(cedula);
    }

    @Transactional
    @Override
    public Persona save(Persona persona) {
        validarDatosPersona(persona);
        verificarDuplicado(persona.getCedula(), persona.getCorreo());

        persona.setId(null); // Asegurar que no se intente sobrescribir una persona existente
        return personaRepository.save(persona);
    }

    @Transactional
    @Override
    public Optional<Persona> update(Persona persona) {
        return personaRepository.findById(persona.getId())
                .map(existingPersona -> {
                    validarDatosPersona(persona);

                    // Si cambia la cédula o el correo, verificamos duplicados
                    if (!existingPersona.getCedula().equals(persona.getCedula()) ||
                            !existingPersona.getCorreo().equals(persona.getCorreo())) {
                        verificarDuplicado(persona.getCedula(), persona.getCorreo());
                    }

                    existingPersona.setNombre(persona.getNombre());
                    existingPersona.setApellido(persona.getApellido());
                    existingPersona.setCedula(persona.getCedula());
                    existingPersona.setCorreo(persona.getCorreo());
                    existingPersona.setPassword(persona.getPassword());
                    existingPersona.setFechaNacimiento(persona.getFechaNacimiento());
                    existingPersona.setTelefono(persona.getTelefono());
                    existingPersona.setDireccion(persona.getDireccion());
                    existingPersona.setEstado(persona.getEstado());
                    existingPersona.setGenero(persona.getGenero());
                    existingPersona.setRol(persona.getRol());
                    return Optional.of(personaRepository.save(existingPersona));
                }).orElseThrow(() -> new PersonaNoEncontradaException("No se puede actualizar: Persona con ID " + persona.getId() + " no encontrada"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!personaRepository.existsById(id)) {
            throw new PersonaNoEncontradaException("No se puede eliminar: Persona con ID " + id + " no encontrada");
        }
        personaRepository.deleteById(id);
    }

    private void validarDatosPersona(Persona persona) {
        if (persona.getNombre() == null || persona.getNombre().isBlank() ||
                persona.getApellido() == null || persona.getApellido().isBlank() ||
                persona.getCedula() == null || !PATRON_CEDULA.matcher(persona.getCedula()).matches() ||
                persona.getCorreo() == null || !PATRON_CORREO.matcher(persona.getCorreo()).matches()) {
            throw new DatosPersonaInvalidosException("Datos inválidos: Asegúrese de ingresar nombre y apellido válidos, cédula de 10 dígitos y correo correcto.");
        }
    }

    private void verificarDuplicado(String cedula, String correo) {
        if (personaRepository.existsByCedula(cedula)) {
            throw new PersonaYaRegistradaException("Ya existe una persona con la cédula: " + cedula);
        }
        if (personaRepository.existsByCorreo(correo)) {
            throw new PersonaYaRegistradaException("Ya existe una persona con el correo: " + correo);
        }
    }

    @Override
    public Optional<Persona> findByCorreo(String correo) {
        return personaRepository.findByCorreo(correo);
    }
}
