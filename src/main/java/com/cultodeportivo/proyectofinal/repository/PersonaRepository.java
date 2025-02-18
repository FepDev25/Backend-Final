package com.cultodeportivo.proyectofinal.repository;

import com.cultodeportivo.proyectofinal.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    boolean existsByCedula(String cedula);
    boolean existsByCorreo(String correo);
    Optional<Persona> findByCedula(String cedula);
    Optional<Persona> findByCorreo(String correo);
}
