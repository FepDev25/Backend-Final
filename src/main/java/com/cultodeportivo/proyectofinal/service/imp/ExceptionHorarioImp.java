package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.ExceptionHorario;
import com.cultodeportivo.proyectofinal.repository.ExceptionHorarioRepository;
import com.cultodeportivo.proyectofinal.service.ExceptionHorarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExceptionHorarioImp implements ExceptionHorarioService {

    private final ExceptionHorarioRepository exceptionHorarioRepository;

    public ExceptionHorarioImp(ExceptionHorarioRepository exceptionHorarioRepository) {
        this.exceptionHorarioRepository = exceptionHorarioRepository;
    }

    @Override
    public Optional<ExceptionHorario> findById(Long id) {
        return exceptionHorarioRepository.findById(id);
    }

    @Override
    public List<ExceptionHorario> findAll() {
        return exceptionHorarioRepository.findAll();
    }

    @Transactional
    @Override
    public ExceptionHorario save(ExceptionHorario exceptionHorario) {
        return exceptionHorarioRepository.save(exceptionHorario);
    }

    @Transactional
    @Override
    public Optional<ExceptionHorario> update(ExceptionHorario exceptionHorario) {
        return exceptionHorarioRepository.findById(exceptionHorario.getId())
                .map(existingException -> {
                    existingException.setFecha(exceptionHorario.getFecha());
                    existingException.setHoraApertura(exceptionHorario.getHoraApertura());
                    existingException.setHoraCierre(exceptionHorario.getHoraCierre());
                    existingException.setCierreTodoDia(exceptionHorario.isCierreTodoDia());
                    return Optional.of(exceptionHorarioRepository.save(existingException));
                }).orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: ExceptionHorario con ID " + exceptionHorario.getId() + " no encontrado"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!exceptionHorarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: ExceptionHorario con ID " + id + " no encontrado");
        }
        exceptionHorarioRepository.deleteById(id);
    }
}
