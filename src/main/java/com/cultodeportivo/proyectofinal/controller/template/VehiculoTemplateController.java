package com.cultodeportivo.proyectofinal.controller.template;

import com.cultodeportivo.proyectofinal.exception.exceptions.ResourceNotFoundException;
import com.cultodeportivo.proyectofinal.model.Usuario;
import com.cultodeportivo.proyectofinal.model.Vehiculo;
import com.cultodeportivo.proyectofinal.service.UsuarioService;
import com.cultodeportivo.proyectofinal.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoTemplateController {

    private final VehiculoService vehiculoService;
    private final UsuarioService usuarioService;

    public VehiculoTemplateController(VehiculoService vehiculoService, UsuarioService usuarioService) {
        this.vehiculoService = vehiculoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        model.addAttribute("vehiculos", vehiculoService.findAll());
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("usuarioVehiculos", emparejador());
        return "registroVehiculo";
    }

    @PostMapping("/registro")
    public String registrarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo, @ModelAttribute("cedula") String cedula, Model model) {
        Optional<Usuario> usuarioOpt = usuarioService.findByPersona_Cedula(cedula);

        if (usuarioOpt.isEmpty()) {
            model.addAttribute("error", "No se encontró un usuario con la cédula ingresada.");
            return "registroVehiculo";
        }

        Usuario usuario = usuarioOpt.get();
        vehiculo.setUsuario(usuario);
        vehiculoService.save(vehiculo);

        model.addAttribute("mensaje", "Vehículo registrado con éxito para el usuario: " + usuario.getPersona().getNombre());
        model.addAttribute("vehiculo", new Vehiculo());

        return "registroVehiculo";
    }

    public Map<Usuario, List<Vehiculo>> emparejador() {
        Map<Usuario, List<Vehiculo>> map = new HashMap<>();

        List<Usuario> usuarios = usuarioService.findAll();
        for (Usuario usuario : usuarios) {
            List<Vehiculo> vehiculos = vehiculoService.findByUsuario_Id(usuario.getId());
            map.put(usuario, vehiculos);
        }

        return map;
    }

}
