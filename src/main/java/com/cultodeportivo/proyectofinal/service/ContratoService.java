package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Contrato;

import java.util.List;
import java.util.Optional;

public interface ContratoService {
    List<Contrato> findAll();
    Optional<Contrato> findById(Long id);
    Contrato save(Contrato contrato);
    Optional<Contrato> update(Contrato contrato);
    void deleteById(Long id);
}
