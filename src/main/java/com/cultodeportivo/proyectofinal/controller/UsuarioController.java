package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.Usuario;
import com.cultodeportivo.proyectofinal.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok().body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> findByCorreo(@PathVariable String correo) {
        return usuarioService.findByPersona_Correo(correo)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con correo " + correo + " no encontrado"));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        Usuario usuarioSaved = usuarioService.save(usuario);
        return ResponseEntity.ok().body(usuarioSaved);
    }

    @PutMapping
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario) {
        return usuarioService.update(usuario)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: Usuario con ID " + usuario.getId() + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
