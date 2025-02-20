package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.Vehiculo;
import com.cultodeportivo.proyectofinal.service.VehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = {"http://localhost:4200", "https://parqueadero-public.web.app"})
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> getAllVehiculos() {
        List<Vehiculo> vehiculos = vehiculoService.findAll();
        return ResponseEntity.ok().body(vehiculos);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Vehiculo>> getAllVehiculosUser(@PathVariable Long id) {
        List<Vehiculo> vehiculos = vehiculoService.findByUsuario_Id(id);
        return ResponseEntity.ok().body(vehiculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable Long id) {
        return vehiculoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo con ID " + id + " no encontrado"));
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Vehiculo> getVehiculoByPlaca(@PathVariable String placa) {
        return vehiculoService.findByPlaca(placa)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo con placa " + placa + " no encontrado"));
    }

    @PostMapping
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        vehiculo.setId(null);
        Vehiculo vehiculoSaved = vehiculoService.save(vehiculo);
        return ResponseEntity.ok().body(vehiculoSaved);
    }

    @PutMapping
    public ResponseEntity<Vehiculo> updateVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.update(vehiculo)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: Vehículo con ID " + vehiculo.getId() + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable Long id) {
        vehiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
