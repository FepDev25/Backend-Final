package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.model.Rol;
import com.cultodeportivo.proyectofinal.service.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = {"http://localhost:4200", "https://parqueadero-public.web.app"})
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        List<Rol> roles = rolService.findAll();
        return ResponseEntity.ok().body(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> findById(@PathVariable Long id) {
        Optional<Rol> rol = rolService.findById(id);
        return rol.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}