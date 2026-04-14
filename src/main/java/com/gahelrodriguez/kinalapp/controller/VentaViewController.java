package com.gahelrodriguez.kinalapp.controller;

import com.gahelrodriguez.kinalapp.entity.Venta;
import com.gahelrodriguez.kinalapp.service.IVentaService;
import com.gahelrodriguez.kinalapp.service.IClienteService;
import com.gahelrodriguez.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ventas/vista")
public class VentaViewController {

    private final IVentaService ventaService;
    private final IClienteService clienteService;
    private final IUsuarioService usuarioService;

    public VentaViewController(IVentaService ventaService, IClienteService clienteService, IUsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("ventas", ventaService.listarTodos());
        model.addAttribute("titulo", "Lista de Ventas");
        return "ventas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("titulo", "Nueva Venta");
        model.addAttribute("accion", "guardar");
        return "ventas/formulario";
    }

    @GetMapping("/editar/{codigo}")
    public String mostrarFormularioEditar(@PathVariable Long codigo, Model model) {
        Venta venta = ventaService.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("titulo", "Editar Venta");
        model.addAttribute("accion", "actualizar");
        return "ventas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta) {
        ventaService.guardar(venta);
        return "redirect:/ventas/vista";
    }

    @PostMapping("/actualizar/{codigo}")
    public String actualizar(@PathVariable Long codigo, @ModelAttribute Venta venta) {
        ventaService.actualizar(codigo, venta);
        return "redirect:/ventas/vista";
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable Long codigo) {
        ventaService.eliminar(codigo);
        return "redirect:/ventas/vista";
    }
}
