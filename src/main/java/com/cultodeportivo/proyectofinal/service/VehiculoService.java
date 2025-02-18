package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Vehiculo;

import java.util.List;
import java.util.Optional;

public interface VehiculoService {
    List<Vehiculo> findAll();
    Optional<Vehiculo> findById(Long id);
    Vehiculo save(Vehiculo vehiculo);
    Optional<Vehiculo> update(Vehiculo vehiculo);
    void deleteById(Long id);
    List<Vehiculo> findByUsuario_Id(Long id);
    Optional<Vehiculo> findByPlaca(String placa);
}
