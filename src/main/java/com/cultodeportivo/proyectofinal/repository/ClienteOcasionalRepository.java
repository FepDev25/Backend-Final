package com.cultodeportivo.proyectofinal.repository;

import com.cultodeportivo.proyectofinal.model.ClienteOcasional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteOcasionalRepository extends JpaRepository<ClienteOcasional, Long> {
    boolean existsByPlaca(String placa);
}
