package com.cultodeportivo.proyectofinal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "tarifas")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "activa")
    private Boolean activa;

    @Override
    public String toString() {
        return "Tarifa{" +
                "id=" + id +
                ", valor=" + valor +
                '}';
    }
}
