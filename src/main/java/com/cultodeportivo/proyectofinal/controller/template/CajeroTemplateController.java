package com.cultodeportivo.proyectofinal.controller.template;

import com.cultodeportivo.proyectofinal.model.Cajero;
import com.cultodeportivo.proyectofinal.model.Persona;
import com.cultodeportivo.proyectofinal.model.Rol;
import com.cultodeportivo.proyectofinal.service.CajeroService;
import com.cultodeportivo.proyectofinal.service.PersonaService;
import com.cultodeportivo.proyectofinal.service.RolService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/cajeros")
public class CajeroTemplateController {

    private final CajeroService cajeroService;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;
    private final PersonaService personaService;

    public CajeroTemplateController(CajeroService cajeroService, RolService rolService, PasswordEncoder passwordEncoder, PersonaService personaService) {
        this.cajeroService = cajeroService;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
        this.personaService = personaService;
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cajero", new Cajero());
        model.addAttribute("cajeros", cajeroService.findAll());
        return "registroCajero";
    }


    @PostMapping("/registro")
    public String registrarCajero(@ModelAttribute Cajero cajero, Model model) {
        Optional<Rol> rol = rolService.findByNombre("Cajero");

        if (rol.isEmpty()) {
            model.addAttribute("error", "El rol de cajero no existe.");
            return "registroCajero";
        }

        Persona persona = cajero.getPersona();
        persona.setRol(rol.get());
        persona.setEstado("ACTIVO");
        persona.setPassword(passwordEncoder.encode(persona.getPassword()));

        Persona personaGuardada = personaService.save(persona);

        cajero.setPersona(personaGuardada);
        cajero.setFechaIngreso(LocalDate.now());

        cajeroService.save(cajero);

        model.addAttribute("mensaje", "Cajero registrado con éxito: " + persona.getNombre());
        model.addAttribute("cajero", new Cajero());

        return "registroCajero";
    }


}
