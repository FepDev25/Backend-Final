package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaService {
    List<Persona> findAll();
    Optional<Persona> findById(Long id);
    Optional<Persona> findByCedula(String cedula);
    Persona save(Persona persona);
    Optional<Persona> update(Persona persona);
    void deleteById(Long id);
    Optional<Persona> findByCorreo(String correo);
}
