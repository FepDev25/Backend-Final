package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    List<Ticket> findAll();
    Optional<Ticket> findById(Long id);
    Ticket save(Ticket ticket);
    Optional<Ticket> update(Ticket ticket);
    void deleteById(Long id);
}
