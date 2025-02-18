package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.EspacioNoDisponibleException;
import com.cultodeportivo.proyectofinal.exception.exceptions.EspacioInexistenteException;
import com.cultodeportivo.proyectofinal.exception.exceptions.EstadoInvalidoException;
import com.cultodeportivo.proyectofinal.model.Espacio;
import com.cultodeportivo.proyectofinal.repository.EspacioRepository;
import com.cultodeportivo.proyectofinal.service.EspacioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspacioServiceImp implements EspacioService {

    private final EspacioRepository espacioRepository;

    public EspacioServiceImp(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    @Override
    public List<Espacio> findAll() {
        return espacioRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Espacio::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Espacio> findById(Long id) {
        return espacioRepository.findById(id);
    }

    @Transactional
    @Override
    public Espacio save(Espacio espacio) {
        if (espacio.getId() != null && espacioRepository.existsById(espacio.getId())) {
            throw new IllegalArgumentException("Espacio con ID ya existe. Usa update.");
        }
        validarEstado(espacio.getEstado());
        return espacioRepository.save(espacio);
    }

    @Transactional
    @Override
    public Optional<Espacio> update(Espacio espacio) {
        return espacioRepository.findById(espacio.getId())
                .map(existingEspacio -> {
                    validarEstado(espacio.getEstado());
                    existingEspacio.setOcupado(espacio.isOcupado());
                    existingEspacio.setReservado(espacio.isReservado());
                    existingEspacio.setEstado(espacio.getEstado());
                    return Optional.of(espacioRepository.save(existingEspacio));
                }).orElseThrow(() -> new EspacioInexistenteException("Espacio con ID " + espacio.getId() + " no encontrado"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!espacioRepository.existsById(id)) {
            throw new EspacioInexistenteException("No se puede eliminar: Espacio con ID " + id + " no encontrado");
        }
        espacioRepository.deleteById(id);
    }

    @Override
    public boolean isReservado(Long id) {
        return espacioRepository.findById(id)
                .map(Espacio::isReservado)
                .orElseThrow(() -> new EspacioInexistenteException("Espacio con ID " + id + " no encontrado"));
    }

    @Override
    public boolean isOcupado(Long id) {
        return espacioRepository.findById(id)
                .map(Espacio::isOcupado)
                .orElseThrow(() -> new EspacioInexistenteException("Espacio con ID " + id + " no encontrado"));
    }

    @Transactional
    @Override
    public Optional<Espacio> marcarReservado(Long id) {
        return espacioRepository.findById(id).map(espacio -> {
            if (espacio.isReservado() || espacio.isOcupado()) {
                throw new EspacioNoDisponibleException("El espacio con ID " + id + " ya está ocupado o reservado.");
            }
            espacio.setReservado(true);
            espacio.setEstado("RESERVADO");
            return Optional.of(espacioRepository.save(espacio));
        }).orElseThrow(() -> new EspacioInexistenteException("Espacio con ID " + id + " no encontrado"));
    }

    @Transactional
    @Override
    public Optional<Espacio> marcarOcupado(Long id) {
        return espacioRepository.findById(id).map(espacio -> {
            if (espacio.isReservado() || espacio.isOcupado()) {
                throw new EspacioNoDisponibleException("El espacio con ID " + id + " ya está ocupado o reservado.");
            }
            espacio.setOcupado(true);
            espacio.setEstado("OCUPADO");
            return Optional.of(espacioRepository.save(espacio));
        }).orElseThrow(() -> new EspacioInexistenteException("Espacio con ID " + id + " no encontrado"));
    }

    @Transactional
    @Override
    public Optional<Espacio> desmarcarReservado(Long id) {
        return espacioRepository.findById(id).map(espacio -> {
            if (!espacio.isReservado()) {
                throw new EspacioNoDisponibleException("El espacio con ID " + id + " no estaba reservado.");
            }
            espacio.setReservado(false);
            espacio.setEstado("DISPONIBLE");
            return Optional.of(espacioRepository.save(espacio));
        }).orElseThrow(() -> new EspacioInexistenteException("Espacio con ID " + id + " no encontrado"));
    }

    @Transactional
    @Override
    public Optional<Espacio> desmarcarOcupado(Long id) {
        return espacioRepository.findById(id).map(espacio -> {
            if (!espacio.isOcupado()) {
                throw new EspacioNoDisponibleException("El espacio con ID " + id + " no estaba ocupado.");
            }
            espacio.setOcupado(false);
            espacio.setEstado("DISPONIBLE");
            return Optional.of(espacioRepository.save(espacio));
        }).orElseThrow(() -> new EspacioInexistenteException("Espacio con ID " + id + " no encontrado"));
    }

    private void validarEstado(String estado) {
        List<String> estadosValidos = List.of("DISPONIBLE", "OCUPADO", "RESERVADO");
        if (!estadosValidos.contains(estado.toUpperCase())) {
            throw new EstadoInvalidoException("Estado inválido. Estados permitidos: " + estadosValidos);
        }
    }
}
