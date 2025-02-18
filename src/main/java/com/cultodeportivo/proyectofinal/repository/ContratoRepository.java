package com.cultodeportivo.proyectofinal.repository;

import com.cultodeportivo.proyectofinal.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    boolean existsByUsuarioIdAndEspacioIdAndFechaFinAfter(Long usuarioId, Long espacioId, LocalDate fecha);
}
