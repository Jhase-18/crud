package com.ejemplo.crud.controller;

import com.ejemplo.crud.model.Usuario;
import com.ejemplo.crud.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "listar-usuarios";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro-usuario";
    }

    @PostMapping
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicion(@PathVariable("id") Long id, Model model) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "editar-usuario";
        } else {
            return "redirect:/usuarios";
        }
    }

    @PostMapping("/editar/{id}")
    public String actualizarUsuario(@PathVariable("id") Long id, @ModelAttribute("usuario") Usuario usuario) {
        usuario.setId(id);
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }
}
