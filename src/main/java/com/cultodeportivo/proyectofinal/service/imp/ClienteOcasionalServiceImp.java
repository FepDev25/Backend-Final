package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.ClienteOcasionalNoEncontradoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.ClienteOcasionalDuplicadoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.ClienteOcasionalNombreInvalidoException;
import com.cultodeportivo.proyectofinal.model.ClienteOcasional;
import com.cultodeportivo.proyectofinal.repository.ClienteOcasionalRepository;
import com.cultodeportivo.proyectofinal.service.ClienteOcasionalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ClienteOcasionalServiceImp implements ClienteOcasionalService {

    private final ClienteOcasionalRepository clienteOcasionalRepository;
    private static final Pattern NOMBRE_VALIDO = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");

    public ClienteOcasionalServiceImp(ClienteOcasionalRepository clienteOcasionalRepository) {
        this.clienteOcasionalRepository = clienteOcasionalRepository;
    }

    @Override
    public List<ClienteOcasional> findAll() {
        return clienteOcasionalRepository.findAll();
    }

    @Override
    public Optional<ClienteOcasional> findById(Long id) {
        return clienteOcasionalRepository.findById(id);
    }

    @Transactional
    @Override
    public ClienteOcasional save(ClienteOcasional clienteOcasional) {
        validarClienteOcasional(clienteOcasional);
        verificarDuplicado(clienteOcasional);

        return clienteOcasionalRepository.save(clienteOcasional);
    }

    @Transactional
    @Override
    public Optional<ClienteOcasional> update(ClienteOcasional clienteOcasional) {
        return clienteOcasionalRepository.findById(clienteOcasional.getId())
                .map(existingCliente -> {
                    validarClienteOcasional(clienteOcasional);

                    if (!existingCliente.getPlaca().equals(clienteOcasional.getPlaca())) {
                        verificarDuplicado(clienteOcasional);
                    }

                    existingCliente.setNombre(clienteOcasional.getNombre());
                    existingCliente.setPlaca(clienteOcasional.getPlaca());

                    return Optional.of(clienteOcasionalRepository.save(existingCliente));
                }).orElseThrow(() -> new ClienteOcasionalNoEncontradoException("No se puede actualizar: Cliente Ocasional con ID " + clienteOcasional.getId() + " no encontrado"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!clienteOcasionalRepository.existsById(id)) {
            throw new ClienteOcasionalNoEncontradoException("No se puede eliminar: Cliente Ocasional con ID " + id + " no encontrado");
        }
        clienteOcasionalRepository.deleteById(id);
    }

    private void validarClienteOcasional(ClienteOcasional clienteOcasional) {
        if (clienteOcasional.getNombre() == null || clienteOcasional.getNombre().trim().isEmpty()) {
            throw new ClienteOcasionalNombreInvalidoException("El nombre del cliente ocasional no puede estar vacío.");
        }

        if (!NOMBRE_VALIDO.matcher(clienteOcasional.getNombre()).matches()) {
            throw new ClienteOcasionalNombreInvalidoException("El nombre del cliente ocasional contiene caracteres inválidos.");
        }

        if (clienteOcasional.getPlaca() == null || clienteOcasional.getPlaca().trim().isEmpty()) {
            throw new ClienteOcasionalNombreInvalidoException("La placa del cliente ocasional no puede estar vacía.");
        }
    }

    private void verificarDuplicado(ClienteOcasional clienteOcasional) {
        if (clienteOcasionalRepository.existsByPlaca(clienteOcasional.getPlaca())) {
            throw new ClienteOcasionalDuplicadoException("Ya existe un cliente ocasional con la placa: " + clienteOcasional.getPlaca());
        }
    }
}
