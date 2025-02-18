package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Espacio;

import java.util.List;
import java.util.Optional;

public interface EspacioService {
    List<Espacio> findAll();
    Optional<Espacio> findById(Long id);
    Espacio save(Espacio espacio);
    Optional<Espacio> update(Espacio espacio);
    void deleteById(Long id);
    boolean isReservado(Long id);
    boolean isOcupado(Long id);
    Optional<Espacio> marcarReservado(Long id);
    Optional<Espacio> marcarOcupado(Long id);
    Optional<Espacio> desmarcarReservado(Long id);
    Optional<Espacio> desmarcarOcupado(Long id);
}
