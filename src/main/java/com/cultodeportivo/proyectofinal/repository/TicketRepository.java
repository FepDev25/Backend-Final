package com.cultodeportivo.proyectofinal.repository;

import com.cultodeportivo.proyectofinal.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
