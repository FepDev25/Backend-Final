package com.cultodeportivo.proyectofinal.service;

import com.cultodeportivo.proyectofinal.model.TipoHorario;

import java.util.List;

public interface TipoHorarioService {
    List<TipoHorario> findAll();
    TipoHorario findById(Long id);
}
