package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    Optional<Rol> findById(Long id);
    List<Rol> findAll();
}
