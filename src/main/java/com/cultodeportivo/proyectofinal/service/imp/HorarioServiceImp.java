package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.HorarioInvalidoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.HorarioDuplicadoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.HorarioFueraDeRangoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.Horario;
import com.cultodeportivo.proyectofinal.repository.HorarioRepository;
import com.cultodeportivo.proyectofinal.service.HorarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class HorarioServiceImp implements HorarioService {

    private final HorarioRepository horarioRepository;

    public HorarioServiceImp(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    @Override
    public Optional<Horario> findById(Long id) {
        return horarioRepository.findById(id);
    }

    @Override
    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    @Transactional
    @Override
    public Horario save(Horario horario) {
        validarHorario(horario);
        return horarioRepository.save(horario);
    }

    @Transactional
    @Override
    public Optional<Horario> update(Horario horario) {
        return horarioRepository.findById(horario.getId())
                .map(existingHorario -> {
                    validarHorario(horario);
                    existingHorario.setHoraApertura(horario.getHoraApertura());
                    existingHorario.setHoraCierre(horario.getHoraCierre());
                    // Asegurar que otros atributos relevantes se actualicen
                    return Optional.of(horarioRepository.save(existingHorario));
                }).orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: Horario con ID " + horario.getId() + " no encontrado"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!horarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Horario con ID " + id + " no encontrado");
        }
        horarioRepository.deleteById(id);
    }

    @Override
    public boolean isClosed(Long id) {
        return horarioRepository.findById(id)
                .map(horario -> {
                    LocalTime now = LocalTime.now();
                    return now.isBefore(horario.getHoraApertura()) || now.isAfter(horario.getHoraCierre());
                }).orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con ID: " + id));
    }

    // 📌 Método para validar los horarios antes de guardarlos o actualizarlos
    private void validarHorario(Horario horario) {
        // ✅ Validación 1: La hora de apertura no puede ser después de la hora de cierre
        if (horario.getHoraApertura().isAfter(horario.getHoraCierre())) {
            throw new HorarioInvalidoException("La hora de apertura no puede ser después de la hora de cierre.");
        }

        // ✅ Validación 2: La hora de apertura y cierre deben estar en el rango de 06:00 a 23:00
        if (horario.getHoraApertura().isBefore(LocalTime.of(6, 0)) || horario.getHoraCierre().isAfter(LocalTime.of(23, 0))) {
            throw new HorarioFueraDeRangoException("El horario debe estar dentro del rango permitido (06:00 - 23:00).");
        }

        // ✅ Validación 3: No permitir horarios duplicados (optimizado con consulta a BD)
        if (horarioRepository.existsByHoraAperturaAndHoraCierre(horario.getHoraApertura(), horario.getHoraCierre())) {
            throw new HorarioDuplicadoException("Ya existe un horario con la misma apertura y cierre.");
        }
    }
}
