package com.cultodeportivo.proyectofinal.service.imp;

import com.cultodeportivo.proyectofinal.model.TipoHorario;
import com.cultodeportivo.proyectofinal.repository.TipoHorarioRepository;
import com.cultodeportivo.proyectofinal.service.TipoHorarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoHorarioServiceImp implements TipoHorarioService {

    private final TipoHorarioRepository tipoHorarioRepository;

    public TipoHorarioServiceImp(TipoHorarioRepository tipoHorarioService) {
        this.tipoHorarioRepository = tipoHorarioService;
    }

    @Override
    public List<TipoHorario> findAll() {
        return tipoHorarioRepository.findAll();
    }

    @Override
    public TipoHorario findById(Long id) {
        return tipoHorarioRepository.findById(id).orElse(null);
    }
}
