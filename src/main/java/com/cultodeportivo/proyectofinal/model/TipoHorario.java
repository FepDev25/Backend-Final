package com.cultodeportivo.proyectofinal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = ("tipo_horarios"))
public class TipoHorario {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "tipoHorario", cascade = CascadeType.ALL)
    private List<Horario> horarios;

    @Override
    public String toString() {
        return "TipoHorario{" +
                "id=" + id +
                ", description='" + descripcion + '\'' +
                '}';
    }
}
