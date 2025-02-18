package com.cultodeportivo.proyectofinal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@Table(name = "excepciones_horarios")
public class ExceptionHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_apertura")
    private LocalTime horaApertura;

    @Column(name = "hora_cierre")
    private LocalTime horaCierre;

    @Column(name = "cierre_todo_dia", nullable = false)
    private boolean cierreTodoDia;

    @ManyToOne
    @JoinColumn(name = "horario_id", nullable = false)
    private Horario horario;

    @Override
    public String toString() {
        return "ExceptionHorario{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", horaApertura=" + horaApertura +
                ", horaCierre=" + horaCierre +
                ", cierreTodoDia=" + cierreTodoDia +
                ", horario=" + horario +
                '}';
    }
}
