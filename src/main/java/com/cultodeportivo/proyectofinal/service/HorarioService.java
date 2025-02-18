package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Horario;

import java.util.List;
import java.util.Optional;

public interface HorarioService {
    Optional<Horario> findById(Long id);
    List<Horario> findAll();
    Horario save(Horario horario);
    Optional<Horario> update(Horario horario);
    void deleteById(Long id);
    boolean isClosed(Long id);
}
