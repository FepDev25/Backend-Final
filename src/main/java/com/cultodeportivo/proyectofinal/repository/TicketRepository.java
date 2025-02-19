package com.cultodeportivo.proyectofinal.repository;

import com.cultodeportivo.proyectofinal.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
