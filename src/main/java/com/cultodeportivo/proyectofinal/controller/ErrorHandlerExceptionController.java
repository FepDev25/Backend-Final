package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.Error;
import com.cultodeportivo.proyectofinal.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ErrorHandlerExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> resourceNotFound(ResourceNotFoundException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), "Recurso no encontrado");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Excepciones horarias

    @ExceptionHandler(HorarioInvalidoException.class)
    public ResponseEntity<Error> handleHorarioInvalido(HorarioInvalidoException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Horario Inválido");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HorarioDuplicadoException.class)
    public ResponseEntity<Error> handleHorarioDuplicado(HorarioDuplicadoException e) {
        Error error = new Error(HttpStatus.CONFLICT.value(), new Date(), e.getMessage(), "Horario Duplicado");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HorarioFueraDeRangoException.class)
    public ResponseEntity<Error> handleHorarioFueraDeRango(HorarioFueraDeRangoException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Horario Fuera de Rango");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Excepciones Espacios

    @ExceptionHandler(EspacioNoDisponibleException.class)
    public ResponseEntity<Error> handleEspacioNoDisponible(EspacioNoDisponibleException e) {
        Error error = new Error(HttpStatus.CONFLICT.value(), new Date(), e.getMessage(), "Espacio No Disponible");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EspacioInexistenteException.class)
    public ResponseEntity<Error> handleEspacioInexistente(EspacioInexistenteException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), "Espacio No Encontrado");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EstadoInvalidoException.class)
    public ResponseEntity<Error> handleEstadoInvalido(EstadoInvalidoException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Estado Inválido");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Excepciones vehiculos

    @ExceptionHandler(PlacaInvalidaException.class)
    public ResponseEntity<Error> handlePlacaInvalida(PlacaInvalidaException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Placa Inválida");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehiculoNoEncontradoException.class)
    public ResponseEntity<Error> handleVehiculoNoEncontrado(VehiculoNoEncontradoException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), "Vehículo No Encontrado");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VehiculoDuplicadoException.class)
    public ResponseEntity<Error> handleVehiculoDuplicado(VehiculoDuplicadoException e) {
        Error error = new Error(HttpStatus.CONFLICT.value(), new Date(), e.getMessage(), "Vehículo Duplicado");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // Excepciones tarifas
    @ExceptionHandler(TarifaInvalidaException.class)
    public ResponseEntity<Error> handleTarifaInvalida(TarifaInvalidaException e) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), new Date(), e.getMessage(), "Tarifa Inválida");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TarifaNoEncontradaException.class)
    public ResponseEntity<Error> handleTarifaNoEncontrada(TarifaNoEncontradaException e) {
        Error error = new Error(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), "Tarifa No Encontrada");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TarifaDuplicadaException.class)
    public ResponseEntity<Error> handleTarifaDuplicada(TarifaDuplicadaException e) {
        Error error = new Error(HttpStatus.CONFLICT.value(), new Date(), e.getMessage(), "Tarifa Duplicada");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
