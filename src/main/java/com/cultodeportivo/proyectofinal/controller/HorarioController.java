package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.ExceptionHorario;
import com.cultodeportivo.proyectofinal.model.Horario;
import com.cultodeportivo.proyectofinal.model.TipoHorario;
import com.cultodeportivo.proyectofinal.model.Usuario;
import com.cultodeportivo.proyectofinal.service.ExceptionHorarioService;
import com.cultodeportivo.proyectofinal.service.HorarioService;
import com.cultodeportivo.proyectofinal.service.TipoHorarioService;
import com.cultodeportivo.proyectofinal.service.UsuarioService;
import com.cultodeportivo.proyectofinal.service.imp.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "http://localhost:4200")
public class HorarioController {

    private final HorarioService horarioService;
    private final TipoHorarioService tipoHorarioService;
    private final ExceptionHorarioService exceptionHorarioService;
    private final UsuarioService usuariosService;
    private final EmailService emailService;

    public HorarioController(HorarioService horarioService, TipoHorarioService tipoHorarioService, ExceptionHorarioService exceptionHorarioService, UsuarioService usuarioService, EmailService emailService) {
        this.horarioService = horarioService;
        this.tipoHorarioService = tipoHorarioService;
        this.exceptionHorarioService = exceptionHorarioService;
        this.usuariosService = usuarioService;
        this.emailService = emailService;
    }

    // 🟢 Métodos para acceder a los tipos de horarios
    @GetMapping("/tipos")
    public ResponseEntity<List<TipoHorario>> findAllTipos() {
        List<TipoHorario> tipos = tipoHorarioService.findAll();
        return ResponseEntity.ok().body(tipos);
    }

    @GetMapping("/tipos/{id}")
    public ResponseEntity<TipoHorario> findTipoById(@PathVariable Long id) {
        return ResponseEntity.ok().body(tipoHorarioService.findById(id));
    }

    // 🟢 Métodos para los horarios
    @GetMapping
    public ResponseEntity<List<Horario>> findAll() {
        List<Horario> horarios = horarioService.findAll();
        return ResponseEntity.ok().body(horarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Horario> findById(@PathVariable Long id) {
        return horarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Horario con ID " + id + " no encontrado"));
    }

    @PostMapping
    public ResponseEntity<Horario> create(@RequestBody Horario horario) {
        Horario horarioCreated = horarioService.save(horario);
        return ResponseEntity.ok().body(horarioCreated);
    }

    @PutMapping
    public ResponseEntity<Horario> update(@RequestBody Horario horario) {
        return horarioService.update(horario)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: Horario con ID " + horario.getId() + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        horarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 🟢 Métodos para excepciones de horarios
    @GetMapping("/exception-horarios")
    public ResponseEntity<List<ExceptionHorario>> findAllExceptionHorarios() {
        List<ExceptionHorario> exceptionHorarios = exceptionHorarioService.findAll();
        return ResponseEntity.ok().body(exceptionHorarios);
    }

    @GetMapping("/exception-horarios/{id}")
    public ResponseEntity<ExceptionHorario> findExceptionHorarioById(@PathVariable Long id) {
        return exceptionHorarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("ExceptionHorario con ID " + id + " no encontrado"));
    }

    @PostMapping("/exception-horarios")
    public ResponseEntity<ExceptionHorario> agregarExcepcion(@RequestBody ExceptionHorario excepcion) {
        excepcion.setId(null);
        ExceptionHorario nuevaExcepcion = exceptionHorarioService.save(excepcion);

        // 🔹 Obtener todos los usuarios y enviar correo de notificación
//        List<Usuario> usuarios = usuariosService.findAll();
//        for (Usuario usuario : usuarios) {
//            emailService.enviarCorreo(usuario.getPersona().getCorreo(),
//                    "Nueva Excepción de Horario",
//                    "Se ha agrega do una nueva excepción de horario para el día " + excepcion.getFecha());
//        }

        return ResponseEntity.ok(nuevaExcepcion);
    }

    @PutMapping("/exception-horarios")
    public ResponseEntity<ExceptionHorario> updateExceptionHorario(@RequestBody ExceptionHorario exceptionHorario) {
        return exceptionHorarioService.update(exceptionHorario)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: ExceptionHorario con ID " + exceptionHorario.getId() + " no encontrado"));
    }

    @DeleteMapping("/exception-horarios/{id}")
    public ResponseEntity<Void> deleteExceptionHorario(@PathVariable Long id) {
        exceptionHorarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
