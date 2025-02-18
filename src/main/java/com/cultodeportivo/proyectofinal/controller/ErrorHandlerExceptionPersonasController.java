package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.Error;
import com.cultodeportivo.proyectofinal.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ErrorHandlerExceptionPersonasController {

    @ExceptionHandler(PersonaNoEncontradaException.class)
    public ResponseEntity<Error> handlePersonaNoEncontrada(PersonaNoEncontradaException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), "Persona No Encontrada");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonaYaRegistradaException.class)
    public ResponseEntity<Error> handlePersonaYaRegistrada(PersonaYaRegistradaException e) {
        Error error = new Error(HttpStatus.CONFLICT.value(), new Date(), e.getMessage(), "Persona Ya Registrada");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DatosPersonaInvalidosException.class)
    public ResponseEntity<Error> handleDatosPersonaInvalidos(DatosPersonaInvalidosException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Datos de Persona Inválidos");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Error> handleUsuarioNoEncontrado(UsuarioNoEncontradoException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), "Usuario No Encontrado");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioYaRegistradoException.class)
    public ResponseEntity<Error> handleUsuarioYaRegistrado(UsuarioYaRegistradoException e) {
        Error error = new Error(HttpStatus.CONFLICT.value(), new Date(), e.getMessage(), "Usuario Ya Registrado");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DatosUsuarioInvalidosException.class)
    public ResponseEntity<Error> handleDatosUsuarioInvalidos(DatosUsuarioInvalidosException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Datos de Usuario Inválidos");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Excepciones de contratos
    @ExceptionHandler(ContratoFechasInvalidasException.class)
    public ResponseEntity<Error> handleContratoFechasInvalidas(ContratoFechasInvalidasException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Fechas de Contrato Inválidas");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContratoDuplicadoException.class)
    public ResponseEntity<Error> handleContratoDuplicado(ContratoDuplicadoException e) {
        Error error = new Error(HttpStatus.CONFLICT.value(), new Date(), e.getMessage(), "Contrato Duplicado");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ContratoInexistenteException.class)
    public ResponseEntity<Error> handleContratoInexistente(ContratoInexistenteException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), "Contrato No Encontrado");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Excepciones de tickets

    @ExceptionHandler(TicketYaCerradoException.class)
    public ResponseEntity<Error> handleTicketYaCerrado(TicketYaCerradoException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Ticket ya cerrado");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MontoInvalidoException.class)
    public ResponseEntity<Error> handleMontoInvalido(MontoInvalidoException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Monto Inválido");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketNoEncontradoException.class)
    public ResponseEntity<Error> handleTicketNoEncontrado(TicketNoEncontradoException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), "Ticket No Encontrado");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketSinSalidaException.class)
    public ResponseEntity<Error> handleTicketSinSalida(TicketSinSalidaException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Ticket sin salida registrada");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Excepciones Clientes Ocasionales
    @ExceptionHandler(ClienteOcasionalNoEncontradoException.class)
    public ResponseEntity<Error> handleClienteOcasionalNoEncontrado(ClienteOcasionalNoEncontradoException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), "Cliente Ocasional No Encontrado");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteOcasionalDuplicadoException.class)
    public ResponseEntity<Error> handleClienteOcasionalDuplicado(ClienteOcasionalDuplicadoException e) {
        Error error = new Error(HttpStatus.CONFLICT.value(), new Date(), e.getMessage(), "Cliente Ocasional Duplicado");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClienteOcasionalNombreInvalidoException.class)
    public ResponseEntity<Error> handleClienteOcasionalNombreInvalido(ClienteOcasionalNombreInvalidoException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Nombre Inválido");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



}
