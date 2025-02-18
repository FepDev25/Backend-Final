package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.ContratoFechasInvalidasException;
import com.cultodeportivo.proyectofinal.exception.exceptions.ContratoDuplicadoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.ContratoInexistenteException;
import com.cultodeportivo.proyectofinal.model.Contrato;
import com.cultodeportivo.proyectofinal.repository.ContratoRepository;
import com.cultodeportivo.proyectofinal.service.ContratoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoServiceImp implements ContratoService {

    private final ContratoRepository contratoRepository;

    public ContratoServiceImp(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    @Override
    public List<Contrato> findAll() {
        return contratoRepository.findAll();
    }

    @Override
    public Optional<Contrato> findById(Long id) {
        return contratoRepository.findById(id);
    }

    @Transactional
    @Override
    public Contrato save(Contrato contrato) {
        contrato.setId(null);
        validarContrato(contrato);
        return contratoRepository.save(contrato);
    }

    @Transactional
    @Override
    public Optional<Contrato> update(Contrato contrato) {
        return contratoRepository.findById(contrato.getId())
                .map(existingContrato -> {
                    validarContrato(contrato);
                    existingContrato.setFechaInicio(contrato.getFechaInicio());
                    existingContrato.setFechaFin(contrato.getFechaFin());
                    existingContrato.setMontoTotal(contrato.getMontoTotal());
                    existingContrato.setUsuario(contrato.getUsuario());
                    existingContrato.setEspacio(contrato.getEspacio());
                    return Optional.of(contratoRepository.save(existingContrato));
                }).orElseThrow(() -> new ContratoInexistenteException(
                        "No se puede actualizar: Contrato con ID " + contrato.getId() + " no encontrado")
                );
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new ContratoInexistenteException("No se puede eliminar: Contrato con ID " + id + " no encontrado");
        }
        contratoRepository.deleteById(id);
    }

    private void validarContrato(Contrato contrato) {
        // ✅ Validación 1: Fecha de inicio < Fecha de fin
        if (contrato.getFechaInicio() != null && contrato.getFechaFin() != null) {
            if (contrato.getFechaInicio().isAfter(contrato.getFechaFin())) {
                throw new ContratoFechasInvalidasException(
                        "La fecha de inicio no puede ser posterior a la fecha de fin."
                );
            }
        }
        if (contrato.getUsuario() != null && contrato.getEspacio() != null) {
            LocalDate hoy = LocalDate.now();
            boolean existeContratoActivo = contratoRepository.existsByUsuarioIdAndEspacioIdAndFechaFinAfter(
                    contrato.getUsuario().getId(),
                    contrato.getEspacio().getId(),
                    hoy
            );
            if (existeContratoActivo) {
                throw new ContratoDuplicadoException(
                        "Ya existe un contrato activo para este usuario y este espacio."
                );
            }
        }
    }
}
