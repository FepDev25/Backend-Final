package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.exceptions.ContratoInexistenteException;
import com.cultodeportivo.proyectofinal.model.Contrato;
import com.cultodeportivo.proyectofinal.service.ContratoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
@CrossOrigin(origins = "http://localhost:4200")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public ResponseEntity<List<Contrato>> getAllContratos() {
        List<Contrato> contratos = contratoService.findAll();
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> getContratoById(@PathVariable Long id) {
        return contratoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ContratoInexistenteException("Contrato con ID " + id + " no encontrado"));
    }

    @PostMapping
    public ResponseEntity<Contrato> createContrato(@RequestBody Contrato contrato) {
        Contrato contratoCreated = contratoService.save(contrato);
        return ResponseEntity.ok(contratoCreated);
    }

    @PutMapping
    public ResponseEntity<Contrato> updateContrato(@RequestBody Contrato contrato) {
        return contratoService.update(contrato)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ContratoInexistenteException("No se puede actualizar: Contrato con ID " + contrato.getId() + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable Long id) {
        contratoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
