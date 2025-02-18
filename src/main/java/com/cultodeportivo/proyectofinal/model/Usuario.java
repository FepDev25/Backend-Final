package com.cultodeportivo.proyectofinal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Setter
    @Getter
    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", fechaRegistro=" + fechaRegistro +
                ", persona=" + persona +
                '}';
    }
}
