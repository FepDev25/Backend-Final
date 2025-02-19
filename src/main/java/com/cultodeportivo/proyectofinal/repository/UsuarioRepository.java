package com.cultodeportivo.proyectofinal.repository;

import com.cultodeportivo.proyectofinal.model.Persona;
import com.cultodeportivo.proyectofinal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByPersona_Cedula(String cedula);
    boolean existsByPersona_Correo(String correo);

    Optional<Usuario> findByPersona_Correo(String personaCorreo);
    Optional<Usuario> findByPersona_Cedula(String cedula);
    
}
