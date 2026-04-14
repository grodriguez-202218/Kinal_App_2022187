package com.gahelrodriguez.kinalapp.controller;

import com.gahelrodriguez.kinalapp.entity.Producto;
import com.gahelrodriguez.kinalapp.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos/vista")
public class ProductoViewController {

    private final IProductoService productoService;

    public ProductoViewController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("titulo", "Lista de Productos");
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Nuevo Producto");
        model.addAttribute("accion", "guardar");
        return "productos/formulario";
    }

    @GetMapping("/editar/{codigo}")
    public String mostrarFormularioEditar(@PathVariable Long codigo, Model model) {
        Producto producto = productoService.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        model.addAttribute("accion", "actualizar");
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos/vista";
    }

    @PostMapping("/actualizar/{codigo}")
    public String actualizar(@PathVariable Long codigo, @ModelAttribute Producto producto) {
        productoService.actualizar(codigo, producto);
        return "redirect:/productos/vista";
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable Long codigo) {
        productoService.eliminar(codigo);
        return "redirect:/productos/vista";
    }
}
