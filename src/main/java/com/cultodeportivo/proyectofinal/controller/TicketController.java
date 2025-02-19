package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.exceptions.TicketNoEncontradoException;
import com.cultodeportivo.proyectofinal.exception.exceptions.ClienteOcasionalNoEncontradoException;
import com.cultodeportivo.proyectofinal.model.ClienteOcasional;
import com.cultodeportivo.proyectofinal.model.Ticket;
import com.cultodeportivo.proyectofinal.service.ClienteOcasionalService;
import com.cultodeportivo.proyectofinal.service.TicketService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

    private final TicketService ticketService;
    private final ClienteOcasionalService clienteOcasionalService;

    public TicketController(TicketService ticketService, ClienteOcasionalService clienteOcasionalService) {
        this.ticketService = ticketService;
        this.clienteOcasionalService = clienteOcasionalService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.findAll();
        return ResponseEntity.ok().body(tickets);
    }

    @GetMapping("/fecha/{localDate}")
    public ResponseEntity<List<Ticket>> getTicketsDate(@PathVariable String localDate) {
        LocalDate date = LocalDate.parse(localDate);
        List<Ticket> tickets = ticketService.findByFechaEmision(date);
        return ResponseEntity.ok().body(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TicketNoEncontradoException("Ticket con ID " + id + " no encontrado"));
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        ticket.setId(null);
        Ticket ticketCreated = ticketService.save(ticket);
        return ResponseEntity.ok().body(ticketCreated);
    }


    @PutMapping
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) {
        return ticketService.update(ticket)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TicketNoEncontradoException("No se puede actualizar: Ticket con ID " + ticket.getId() + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos para clientes ocasionales

    @GetMapping("/clientes-ocasionales")
    public ResponseEntity<List<ClienteOcasional>> findClientesOcasionales() {
        List<ClienteOcasional> clienteOcasionals = clienteOcasionalService.findAll();
        return ResponseEntity.ok().body(clienteOcasionals);
    }


    @GetMapping("/clientes-ocasionales/{id}")
    public ResponseEntity<ClienteOcasional> findClientesOcasionaleById(@PathVariable Long id) {
        return clienteOcasionalService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ClienteOcasionalNoEncontradoException("Cliente ocasional con ID " + id + " no encontrado"));
    }


    @PostMapping("/clientes-ocasionales")
    public ResponseEntity<ClienteOcasional> createClientesOcasional(@RequestBody ClienteOcasional clienteOcasional) {
        clienteOcasional.setId(null);
        ClienteOcasional newClienteOcasional = clienteOcasionalService.save(clienteOcasional);
        return ResponseEntity.ok().body(newClienteOcasional);
    }

    @PutMapping("/clientes-ocasionales")
    public ResponseEntity<ClienteOcasional> updateClientesOcasional(@RequestBody ClienteOcasional clienteOcasional) {
        return clienteOcasionalService.update(clienteOcasional)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ClienteOcasionalNoEncontradoException("No se puede actualizar: Cliente ocasional con ID " + clienteOcasional.getId() + " no encontrado"));
    }


    @DeleteMapping("/clientes-ocasionales/{id}")
    public ResponseEntity<Void> deleteClientesOcasionaleById(@PathVariable Long id) {
        clienteOcasionalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
