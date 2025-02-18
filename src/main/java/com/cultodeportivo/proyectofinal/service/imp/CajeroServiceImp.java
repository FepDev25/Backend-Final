package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.Cajero;
import com.cultodeportivo.proyectofinal.repository.CajeroRepository;
import com.cultodeportivo.proyectofinal.service.CajeroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CajeroServiceImp implements CajeroService {

    private final CajeroRepository cajeroRepository;

    public CajeroServiceImp(CajeroRepository cajeroRepository) {
        this.cajeroRepository = cajeroRepository;
    }

    @Override
    public List<Cajero> findAll() {
        return cajeroRepository.findAll();
    }

    @Override
    public Optional<Cajero> findById(Long id) {
        return cajeroRepository.findById(id);
    }

    @Transactional
    @Override
    public Cajero save(Cajero cajero) {
        return cajeroRepository.save(cajero);
    }

    @Transactional
    @Override
    public Optional<Cajero> update(Cajero cajero) {
        return cajeroRepository.findById(cajero.getId()).map(existingCajero -> {
            existingCajero.setFechaIngreso(cajero.getFechaIngreso());
            existingCajero.setSueldo(cajero.getSueldo());
            existingCajero.setPersona(cajero.getPersona());
            return Optional.of(cajeroRepository.save(existingCajero)); // Guardamos en BD
        }).orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: Cajero con ID " + cajero.getId() + " no encontrado"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!cajeroRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Cajero con ID " + id + " no encontrado");
        }
        cajeroRepository.deleteById(id);
    }
}
