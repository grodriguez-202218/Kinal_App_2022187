package com.gahelrodriguez.kinalapp.controller;

import com.gahelrodriguez.kinalapp.entity.Venta;
import com.gahelrodriguez.kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/ventas")
//Todas las rutas en este controlador deben empezar con /ventas
public class VentaController {

    //Inyectamos el SERVICIO y NO el repositorio
    //El controlador solo debe tener conexion con el Servicio
    private final IVentaService ventaService;

    //Como buena practica la Inyeccion de dependencias debe hacerse por el constructor
    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Venta>> listar() {
        List<Venta> ventas = ventaService.listarTodos();
        return ResponseEntity.ok(ventas);
        //200 OK con la lista de ventas
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Venta>> listarActivos() {
        List<Venta> ventas = ventaService.listarActivos();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<Venta>> listarInactivos() {
        List<Venta> ventas = ventaService.listarInactivos();
        return ResponseEntity.ok(ventas);
    }

    //{dpi} es una variable de ruta (DPI del cliente a consultar)
    @GetMapping("/cliente/{dpi}")
    public ResponseEntity<List<Venta>> listarPorCliente(@PathVariable String dpi) {
        //@PathVariable: Toma el valor de la URL y lo asigna al parametro dpi
        List<Venta> ventas = ventaService.listarPorCliente(dpi);
        return ResponseEntity.ok(ventas);
    }

    //{codigo} es una variable de ruta (codigo del usuario a consultar)
    @GetMapping("/usuario/{codigo}")
    public ResponseEntity<List<Venta>> listarPorUsuario(@PathVariable Long codigo) {
        List<Venta> ventas = ventaService.listarPorUsuario(codigo);
        return ResponseEntity.ok(ventas);
    }

    //{codigo} es una variable de ruta (valor a buscar)
    @GetMapping("/{codigo}")
    public ResponseEntity<Venta> buscarPorCodigo(@PathVariable Long codigo) {
        //@PathVariable: Toma el valor de la URL y lo asigna al parametro codigo
        return ventaService.buscarPorCodigo(codigo)
                //Si Optional tiene valor, devuelve 200 OK
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear una nueva venta
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta) {
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Venta
        //<?> significa "tipo generico", puede ser una Venta o un String
        try {
            Venta nuevaVenta = ventaService.guardar(venta);
            //Intentamos guardar la venta pero puede lanzar IllegalArgumentException
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
            //201 CREATED (mas especifico que 200 para la creacion de un recurso)
        } catch (IllegalArgumentException e) {
            //Si hay error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error
        }
    }

    //PUT: actualizar venta a traves de su codigo
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigo, @RequestBody Venta venta) {
        try {
            if (!ventaService.existeCodigo(codigo))
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
            //404 NOT FOUND

            //Actualiza la venta, puede lanzar una exception
            Venta ventaActualizada = ventaService.actualizar(codigo, venta);
            return ResponseEntity.ok(ventaActualizada);
            //200 OK con la venta ya actualizada
        } catch (IllegalArgumentException e) {
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            //Cualquier otro error como: venta no encontrada, etc.
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //DELETE: elimina una venta
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigo) {
        //ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try {
            if (!ventaService.existeCodigo(codigo))
                return ResponseEntity.notFound().build();
            //404 si no existe
            ventaService.eliminar(codigo);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }
}