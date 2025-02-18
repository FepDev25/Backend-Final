package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.Tarifa;
import com.cultodeportivo.proyectofinal.service.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
@CrossOrigin(origins = "http://localhost:4200")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @GetMapping
    public ResponseEntity<List<Tarifa>> listarTarifas() {
        List<Tarifa> tarifas = tarifaService.findAll();
        return ResponseEntity.ok().body(tarifas);
    }

    @PostMapping
    public ResponseEntity<Tarifa> crearTarifa(@RequestBody Tarifa tarifa) {
        tarifa.setId(null);
        Tarifa newTarifa = tarifaService.save(tarifa);
        return ResponseEntity.ok().body(newTarifa);
    }

    @PutMapping
    public ResponseEntity<Tarifa> actualizarTarifa(@RequestBody Tarifa tarifa) {
        return tarifaService.update(tarifa)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: Tarifa con ID " + tarifa.getId() + " no encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarifa(@PathVariable Long id) {
        tarifaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> obtenerTarifa(@PathVariable Long id) {
        return tarifaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa con ID " + id + " no encontrada"));
    }

    @GetMapping("/activa")
    public ResponseEntity<Tarifa> obtenerTarifaActiva() {
        return tarifaService.getTarifaActiva()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No hay una tarifa activa en el sistema"));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Tarifa> activarTarifa(@PathVariable Long id) {
        return tarifaService.activarTarifa(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarifa con ID " + id));
    }
}
