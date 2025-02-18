package com.cultodeportivo.proyectofinal.controller;

import com.cultodeportivo.proyectofinal.exception.exceptions.UsuarioNoEncontradoException;
import com.cultodeportivo.proyectofinal.model.Cajero;
import com.cultodeportivo.proyectofinal.model.Persona;
import com.cultodeportivo.proyectofinal.model.Rol;
import com.cultodeportivo.proyectofinal.model.Usuario;
import com.cultodeportivo.proyectofinal.service.CajeroService;
import com.cultodeportivo.proyectofinal.service.PersonaService;
import com.cultodeportivo.proyectofinal.service.RolService;
import com.cultodeportivo.proyectofinal.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.cultodeportivo.proyectofinal.security.TokenJwtConfig.SECRET_KEY;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final PersonaService personaService;
    private final CajeroService cajeroService;
    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(PersonaService personaService, RolService rolService, PasswordEncoder passwordEncoder, UsuarioService usuarioService, CajeroService cajeroService) {
        this.personaService = personaService;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
        this.cajeroService = cajeroService;
    }

    @PostMapping("/register")
    public ResponseEntity<Persona> register(@RequestBody Persona persona) {
        Optional<Rol> rol = rolService.findById(persona.getRol().getId());

        if (rol.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        persona.setPassword(passwordEncoder.encode(persona.getPassword()));
        persona.setRol(rol.get());
        Persona savedPersona = personaService.save(persona);

        if (savedPersona.getRol().getNombre().equals("Cajero")) {
            Cajero cajero = new Cajero();
            cajero.setPersona(savedPersona);
            cajero.setSueldo(new BigDecimal(1500));
            cajero.setFechaIngreso(LocalDate.now());
            cajeroService.save(cajero);
        } else{
            Usuario usuario = new Usuario();
            usuario.setPersona(savedPersona);
            usuario.setFechaRegistro(LocalDate.now());
            usuarioService.save(usuario);
        }

        return ResponseEntity.ok(savedPersona);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String correo = credentials.get("correo");
        String password = credentials.get("password");

        Optional<Persona> personaOptional = personaService.findByCorreo(correo);

        if (personaOptional.isEmpty()) {
            throw new UsuarioNoEncontradoException("No se encontró un usuario con el correo proporcionado.");
        }

        Persona persona = personaOptional.get();
        System.out.println(persona);

        if (!passwordEncoder.matches(password, persona.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Contraseña incorrecta."));
        }

        // Creación del Token JWT
        Claims claims = Jwts.claims()
                .add("authorities", persona.getRol().getNombre())
                .add("username", persona.getCorreo())
                .build();

        String token = Jwts.builder()
                .subject(persona.getCorreo())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", persona.getCorreo());
        response.put("role", persona.getRol().getNombre());
        response.put("message", "Inicio de sesión exitoso.");

        return ResponseEntity.ok(response);
    }

}
