package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.TarifaInvalidaException;
import com.cultodeportivo.proyectofinal.exception.exceptions.TarifaNoEncontradaException;
import com.cultodeportivo.proyectofinal.exception.exceptions.TarifaDuplicadaException;
import com.cultodeportivo.proyectofinal.model.Tarifa;
import com.cultodeportivo.proyectofinal.repository.TarifaRepository;
import com.cultodeportivo.proyectofinal.service.TarifaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TarifaServiceImp implements TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaServiceImp(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    @Override
    public List<Tarifa> findAll() {
        return tarifaRepository.findAll();
    }

    @Override
    public Optional<Tarifa> findById(Long id) {
        return tarifaRepository.findById(id);
    }

    @Transactional
    @Override
    public Tarifa save(Tarifa tarifa) {
        validarTarifa(tarifa);
        verificarDuplicado(tarifa);

        return tarifaRepository.save(tarifa);
    }

    @Transactional
    @Override
    public Optional<Tarifa> update(Tarifa tarifa) {
        return tarifaRepository.findById(tarifa.getId())
                .map(existingTarifa -> {
                    validarTarifa(tarifa);
                    if (!existingTarifa.getValor().equals(tarifa.getValor())) {
                        verificarDuplicado(tarifa);
                    }

                    existingTarifa.setValor(tarifa.getValor());
                    return Optional.of(tarifaRepository.save(existingTarifa));
                }).orElseThrow(() -> new TarifaNoEncontradaException("No se puede actualizar: Tarifa con ID " + tarifa.getId() + " no encontrada"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!tarifaRepository.existsById(id)) {
            throw new TarifaNoEncontradaException("No se puede eliminar: Tarifa con ID " + id + " no encontrada");
        }
        tarifaRepository.deleteById(id);
    }

    @Override
    public Optional<Tarifa> getTarifaActiva() {
        return tarifaRepository.findByActivaTrue();
    }

    @Transactional
    @Override
    public Optional<Tarifa> activarTarifa(Long id) {
        // Desactivar todas las tarifas primero
        desactivarTodas();

        return tarifaRepository.findById(id).map(t -> {
            t.setActiva(true);
            return tarifaRepository.save(t);
        });
    }

    @Transactional
    @Override
    public void desactivarTodas() {
        List<Tarifa> tarifas = tarifaRepository.findAll();
        tarifas.forEach(t -> t.setActiva(false));
        tarifaRepository.saveAll(tarifas);
    }

    private void validarTarifa(Tarifa tarifa) {
        if (tarifa.getValor() == null || tarifa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TarifaInvalidaException("El valor de la tarifa debe ser un número positivo mayor a 0.");
        }
    }

    private void verificarDuplicado(Tarifa tarifa) {
        if (tarifaRepository.existsByValor(tarifa.getValor())) {
            throw new TarifaDuplicadaException("Ya existe una tarifa con el mismo valor: " + tarifa.getValor());
        }
    }
}
