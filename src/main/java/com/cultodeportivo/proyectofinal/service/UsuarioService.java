package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    Optional<Usuario> update(Usuario usuario);
    void deleteById(Long id);
    Optional<Usuario> findByPersona_Correo(String personaCorreo);
}
