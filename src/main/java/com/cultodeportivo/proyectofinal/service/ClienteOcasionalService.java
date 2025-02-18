package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.ClienteOcasional;

import java.util.List;
import java.util.Optional;

public interface ClienteOcasionalService {
    List<ClienteOcasional> findAll();
    Optional<ClienteOcasional> findById(Long id);
    ClienteOcasional save(ClienteOcasional clienteOcasional);
    Optional<ClienteOcasional> update(ClienteOcasional clienteOcasional);
    void deleteById(Long id);
}
