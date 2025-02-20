package com.cultodeportivo.proyectofinal.controller.template;

import com.cultodeportivo.proyectofinal.model.Persona;
import com.cultodeportivo.proyectofinal.model.Rol;
import com.cultodeportivo.proyectofinal.model.Usuario;
import com.cultodeportivo.proyectofinal.service.PersonaService;
import com.cultodeportivo.proyectofinal.service.RolService;
import com.cultodeportivo.proyectofinal.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioTemplateController {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;
    private final PersonaService personaService;

    public UsuarioTemplateController(UsuarioService usuarioService, RolService rolService, PasswordEncoder passwordEncoder, PersonaService personaService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
        this.personaService = personaService;
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarios", usuarioService.findAll());
        return "registroUsuario";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        Optional<Rol> rol = rolService.findByNombre("Usuario");

        if (rol.isEmpty()) {
            model.addAttribute("error", "El rol de usuario no existe.");
            return "registroUsuario";
        }

        Persona persona = usuario.getPersona();
        persona.setRol(rol.get());
        persona.setEstado("ACTIVO");
        persona.setPassword(passwordEncoder.encode(persona.getPassword()));

        Persona personaGuardada = personaService.save(persona);

        usuario.setPersona(personaGuardada);
        usuario.setFechaRegistro(LocalDate.now());

        usuarioService.save(usuario);

        model.addAttribute("mensaje", "Usuario registrado con éxito: " + persona.getNombre());
        model.addAttribute("usuario", new Usuario());

        return "registroUsuario";
    }
}
