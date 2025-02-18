package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Cajero;

import java.util.List;
import java.util.Optional;

public interface CajeroService {
    List<Cajero> findAll();
    Optional<Cajero> findById(Long id);
    Cajero save(Cajero cajero);
    Optional<Cajero> update(Cajero cajero);
    void deleteById(Long id);
}
