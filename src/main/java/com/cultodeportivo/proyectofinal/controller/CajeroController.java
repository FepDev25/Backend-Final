package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.model.Cajero;
import com.cultodeportivo.proyectofinal.service.CajeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cajeros")
@CrossOrigin(origins = "http://localhost:4200")
public class CajeroController {

    private final CajeroService cajeroService;

    public CajeroController(CajeroService cajeroService) {
        this.cajeroService = cajeroService;
    }

    @GetMapping
    public ResponseEntity<List<Cajero>> listar() {
        List<Cajero> cajeros = cajeroService.findAll();
        return ResponseEntity.ok().body(cajeros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cajero> buscar(@PathVariable Long id) {
        return cajeroService.findById(id)
                .map(cajero -> ResponseEntity.ok().body(cajero))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cajero> guardar(@RequestBody Cajero cajero) {
        Cajero cajeroSalida = cajeroService.save(cajero);
        return ResponseEntity.ok().body(cajeroSalida);
    }

    @PutMapping
    public ResponseEntity<Cajero> actualizar(@RequestBody Cajero cajero) {
        return cajeroService.update(cajero)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cajeroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/guardar")
    public String guardarCajero(@ModelAttribute Cajero cajero, Model model) {
        try {
            cajeroService.save(cajero); // Guarda el cajero en la base de datos
            model.addAttribute("mensaje", "Cajero registrado exitosamente");
            model.addAttribute("exito", true);
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al registrar el cajero");
            model.addAttribute("exito", false);
        }
        return "insertar-cajero";
    }
}
