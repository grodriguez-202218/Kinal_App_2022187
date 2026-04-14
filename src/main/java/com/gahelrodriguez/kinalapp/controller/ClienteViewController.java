package com.gahelrodriguez.kinalapp.controller;

import com.gahelrodriguez.kinalapp.entity.Cliente;
import com.gahelrodriguez.kinalapp.service.IClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes/vista")
public class ClienteViewController {

    private final IClienteService clienteService;

    public ClienteViewController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Lista de Clientes");
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        model.addAttribute("accion", "guardar");
        return "clientes/formulario";
    }

    @GetMapping("/editar/{dpi}")
    public String mostrarFormularioEditar(@PathVariable Long dpi, Model model) {
        Cliente cliente = clienteService.busacarPorDPI(dpi)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        model.addAttribute("accion", "actualizar");
        return "clientes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes/vista";
    }

    @PostMapping("/actualizar/{dpi}")
    public String actualizar(@PathVariable Long dpi, @ModelAttribute Cliente cliente) {
        clienteService.actualizar(dpi, cliente);
        return "redirect:/clientes/vista";
    }

    @GetMapping("/eliminar/{dpi}")
    public String eliminar(@PathVariable Long dpi) {
        clienteService.eliminar(dpi);
        return "redirect:/clientes/vista";
    }
}
