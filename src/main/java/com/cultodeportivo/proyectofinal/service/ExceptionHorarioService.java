package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.ExceptionHorario;

import java.util.List;
import java.util.Optional;

public interface ExceptionHorarioService {
    Optional<ExceptionHorario> findById(Long id);
    List<ExceptionHorario> findAll();
    ExceptionHorario save(ExceptionHorario exceptionHorario);
    Optional<ExceptionHorario> update(ExceptionHorario exceptionHorario);
    void deleteById(Long id);
}
