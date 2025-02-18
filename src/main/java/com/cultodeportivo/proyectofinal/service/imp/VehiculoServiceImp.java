package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.PlacaInvalidaException;
import com.cultodeportivo.proyectofinal.exception.exceptions.VehiculoNoEncontradoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.VehiculoDuplicadoException;
import com.cultodeportivo.proyectofinal.model.Vehiculo;
import com.cultodeportivo.proyectofinal.repository.VehiculoRepository;
import com.cultodeportivo.proyectofinal.service.VehiculoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class VehiculoServiceImp implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    // Expresión regular para validar placas: ABC-1234 o ABC123
    private static final Pattern PATRON_PLACA = Pattern.compile("^[A-Z]{3}-?\\d{3,4}$");

    public VehiculoServiceImp(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    @Override
    public Optional<Vehiculo> findById(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Transactional
    @Override
    public Vehiculo save(Vehiculo vehiculo) {
        validarPlaca(vehiculo.getPlaca());
        validarVehiculoDuplicado(vehiculo.getPlaca());

        return vehiculoRepository.save(vehiculo);
    }

    @Transactional
    @Override
    public Optional<Vehiculo> update(Vehiculo vehiculo) {
        return vehiculoRepository.findById(vehiculo.getId())
                .map(existingVehiculo -> {
                    validarPlaca(vehiculo.getPlaca());

                    if (!existingVehiculo.getPlaca().equals(vehiculo.getPlaca())) {
                        validarVehiculoDuplicado(vehiculo.getPlaca());
                    }

                    existingVehiculo.setPlaca(vehiculo.getPlaca());
                    existingVehiculo.setUsuario(vehiculo.getUsuario());
                    return Optional.of(vehiculoRepository.save(existingVehiculo));
                }).orElseThrow(() -> new VehiculoNoEncontradoException("No se puede actualizar: Vehículo con ID " + vehiculo.getId() + " no encontrado"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new VehiculoNoEncontradoException("No se puede eliminar: Vehículo con ID " + id + " no encontrado");
        }
        vehiculoRepository.deleteById(id);
    }

    private void validarPlaca(String placa) {
        if (placa == null || !PATRON_PLACA.matcher(placa).matches()) {
            throw new PlacaInvalidaException("Formato de placa inválido. Debe ser ABC-1234 o ABC123.");
        }
    }

    private void validarVehiculoDuplicado(String placa) {
        if (vehiculoRepository.existsByPlaca(placa)) {
            throw new VehiculoDuplicadoException("Ya existe un vehículo registrado con la placa: " + placa);
        }
    }
}
