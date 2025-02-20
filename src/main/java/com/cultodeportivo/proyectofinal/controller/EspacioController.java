package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.Espacio;
import com.cultodeportivo.proyectofinal.service.EspacioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/espacios")
@CrossOrigin(origins = {"http://localhost:4200", "https://parqueadero-public.web.app"})
public class EspacioController {

    private final EspacioService espacioService;

    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    @GetMapping
    public ResponseEntity<List<Espacio>> findAll() {
        List<Espacio> espacios = espacioService.findAll();
        return ResponseEntity.ok().body(espacios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Espacio> findById(@PathVariable Long id) {
        return espacioService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado con ID " + id));
    }

    @PostMapping
    public ResponseEntity<Espacio> save(@RequestBody Espacio espacio) {
        return ResponseEntity.ok().body(espacioService.save(espacio));
    }

    @PutMapping
    public ResponseEntity<Espacio> update(@RequestBody Espacio espacio) {
        return espacioService.update(espacio)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: Espacio con ID " + espacio.getId() + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspacio(@PathVariable Long id) {
        espacioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reservar")
    public ResponseEntity<Espacio> marcarReservado(@PathVariable Long id) {
        return espacioService.marcarReservado(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede reservar: Espacio con ID " + id + " no encontrado"));
    }

    @PutMapping("/{id}/ocupar")
    public ResponseEntity<Espacio> marcarOcupado(@PathVariable Long id) {
        return espacioService.marcarOcupado(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede ocupar: Espacio con ID " + id + " no encontrado"));
    }

    @PutMapping("/{id}/desmarcar-reservado")
    public ResponseEntity<Espacio> desmarcarReservado(@PathVariable Long id) {
        return espacioService.desmarcarReservado(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede ocupar: Espacio con ID " + id + " no encontrado"));
    }

    @PutMapping("/{id}/desmarcar-ocupado")
    public ResponseEntity<Espacio> desmarcarOcupado(@PathVariable Long id) {
        return espacioService.desmarcarOcupado(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede ocupar: Espacio con ID " + id + " no encontrado"));
    }
}
