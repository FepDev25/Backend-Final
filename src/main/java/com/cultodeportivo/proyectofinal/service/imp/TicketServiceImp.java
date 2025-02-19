package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.exception.exceptions.TicketYaCerradoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.MontoInvalidoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.TicketNoEncontradoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.TicketSinSalidaException;
import com.cultodeportivo.proyectofinal.model.Tarifa;
import com.cultodeportivo.proyectofinal.model.Ticket;
import com.cultodeportivo.proyectofinal.repository.TicketRepository;
import com.cultodeportivo.proyectofinal.service.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImp implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImp(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    @Transactional
    @Override
    public Ticket save(Ticket ticket) {
        validarTicket(ticket);
        return ticketRepository.save(ticket);
    }

    @Transactional
    @Override
    public Optional<Ticket> update(Ticket ticket) {
        return ticketRepository.findById(ticket.getId())
                .map(existingTicket -> {
                    if (existingTicket.getHoraSalida() != null) {
                        throw new TicketYaCerradoException("No se puede modificar un ticket que ya tiene hora de salida registrada.");
                    }

                    validarTicket(ticket);

                    existingTicket.setFechaEmision(ticket.getFechaEmision());
                    existingTicket.setHoraEntrada(ticket.getHoraEntrada());
                    existingTicket.setHoraSalida(ticket.getHoraSalida());
                    existingTicket.setMontoTotal(ticket.getMontoTotal());
                    existingTicket.setEspacio(ticket.getEspacio());
                    existingTicket.setVehiculo(ticket.getVehiculo());
                    existingTicket.setClienteOcasional(ticket.getClienteOcasional());
                    return Optional.of(ticketRepository.save(existingTicket));
                }).orElseThrow(() -> new TicketNoEncontradoException("No se puede actualizar: Ticket con ID " + ticket.getId() + " no encontrado"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new TicketNoEncontradoException("No se puede eliminar: Ticket con ID " + id + " no encontrado");
        }
        ticketRepository.deleteById(id);
    }

    @Override
    public List<Ticket> findByFechaEmision(LocalDate fechaEmision) {
        return ticketRepository.findAll().stream().filter(ticket -> ticket.getFechaEmision().isEqual(fechaEmision) || ticket.getFechaEmision().isAfter(fechaEmision)).collect(Collectors.toList());
    }

    public BigDecimal calcularMonto(Long id, Tarifa tarifa) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNoEncontradoException("No se puede calcular monto: Ticket con ID " + id + " no encontrado"));

        if (ticket.getHoraSalida() == null) {
            throw new TicketSinSalidaException("No se puede calcular el monto de un ticket sin hora de salida registrada.");
        }

        return ticket.getMontoTotal().multiply(tarifa.getValor());
    }

    private void validarTicket(Ticket ticket) {
        if (ticket.getMontoTotal() != null && ticket.getMontoTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new MontoInvalidoException("El monto total del ticket no puede ser negativo.");
        }
    }
}
