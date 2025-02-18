package com.cultodeportivo.proyectofinal.repository;

import com.cultodeportivo.proyectofinal.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    boolean existsByValor(BigDecimal valor);

    Optional<Tarifa> findByActivaTrue(); // Obtiene la tarifa activa
}
