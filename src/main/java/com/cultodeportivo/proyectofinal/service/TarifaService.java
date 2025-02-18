package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Tarifa;

import java.util.List;
import java.util.Optional;

public interface TarifaService {
    List<Tarifa> findAll();
    Optional<Tarifa> findById(Long id);
    Tarifa save(Tarifa tarifa);
    Optional<Tarifa> update(Tarifa tarifa);
    void deleteById(Long id);

    // Métodos adicionales
    Optional<Tarifa> getTarifaActiva();
    Optional<Tarifa> activarTarifa(Long id);
    void desactivarTodas();
}
