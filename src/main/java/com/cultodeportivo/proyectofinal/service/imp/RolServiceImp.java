package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.model.Rol;
import com.cultodeportivo.proyectofinal.repository.RolRepository;
import com.cultodeportivo.proyectofinal.service.RolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImp implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImp(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Optional<Rol> findById(Long id) {
        Rol rol = rolRepository.findById(id).orElse(null);
        return Optional.ofNullable(rol);
    }

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        Rol rol = rolRepository.findByNombre(nombre).orElse(null);
        return Optional.ofNullable(rol);
    }
}
