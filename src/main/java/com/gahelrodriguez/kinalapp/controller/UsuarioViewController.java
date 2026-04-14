package com.gahelrodriguez.kinalapp.controller;

import com.gahelrodriguez.kinalapp.entity.Usuario;
import com.gahelrodriguez.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios/vista")
public class UsuarioViewController {

    private final IUsuarioService usuarioService;

    public UsuarioViewController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("titulo", "Lista de Usuarios");
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("accion", "guardar");
        return "usuarios/formulario";
    }

    @GetMapping("/editar/{codigo}")
    public String mostrarFormularioEditar(@PathVariable Long codigo, Model model) {
        Usuario usuario = usuarioService.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Editar Usuario");
        model.addAttribute("accion", "actualizar");
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/usuarios/vista";
    }

    @PostMapping("/actualizar/{codigo}")
    public String actualizar(@PathVariable Long codigo, @ModelAttribute Usuario usuario) {
        usuarioService.actualizar(codigo, usuario);
        return "redirect:/usuarios/vista";
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable Long codigo) {
        usuarioService.eliminar(codigo);
        return "redirect:/usuarios/vista";
    }
}
