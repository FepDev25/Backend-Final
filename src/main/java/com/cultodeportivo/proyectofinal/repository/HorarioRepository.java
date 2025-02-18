package com.cultodeportivo.proyectofinal.repository;

import com.cultodeportivo.proyectofinal.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    boolean existsByHoraAperturaAndHoraCierre(LocalTime horaApertura, LocalTime horaCierre);
}
