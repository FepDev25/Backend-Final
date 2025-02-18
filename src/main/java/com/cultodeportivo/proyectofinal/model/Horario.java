package com.cultodeportivo.proyectofinal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "horarios")
public class Horario {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "hora_apertura", nullable = false)
    private LocalTime horaApertura;

    @Setter
    @Getter
    @Column(name = "hora_cierre", nullable = false)
    private LocalTime horaCierre;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoHorario tipoHorario;

    @OneToMany(mappedBy = "horario", cascade = CascadeType.ALL)
    private List<ExceptionHorario> exceptionHorarios;

}
