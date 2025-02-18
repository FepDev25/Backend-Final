package com.cultodeportivo.proyectofinal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "espacios")
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ocupado", nullable = false)
    private boolean ocupado;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "reservado", nullable = false)
    private boolean reservado;

}
