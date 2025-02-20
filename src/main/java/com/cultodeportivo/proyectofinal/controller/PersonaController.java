package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.Persona;
import com.cultodeportivo.proyectofinal.service.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = {"http://localhost:4200", "https://parqueadero-public.web.app"})
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<Persona>> findAll() {
        List<Persona> personas = personaService.findAll();
        return ResponseEntity.ok().body(personas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> findById(@PathVariable Long id) {
        return personaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<Persona> findByCedula(@PathVariable String cedula) {
        return personaService.findByCedula(cedula)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró persona con cédula " + cedula));
    }

    @PostMapping
    public ResponseEntity<Persona> save(@RequestBody Persona persona) {
        Persona personaIngresada = personaService.save(persona);
        return ResponseEntity.ok().body(personaIngresada);
    }

    @PutMapping
    public ResponseEntity<Persona> update(@RequestBody Persona persona) {
        return personaService.update(persona)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Persona> getByCorreo(@PathVariable String correo) {
        Optional<Persona> persona = personaService.findByCorreo(correo);

        if (persona.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró una persona con el correo " + correo);
        }

        return ResponseEntity.ok(persona.get());
    }

}
