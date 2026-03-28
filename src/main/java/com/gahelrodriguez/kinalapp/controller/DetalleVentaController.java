package com.gahelrodriguez.kinalapp.controller;

import com.gahelrodriguez.kinalapp.entity.DetalleVenta;
import com.gahelrodriguez.kinalapp.service.IDetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/detalles")
//Todas las rutas en este controlador deben empezar con /detalles
public class DetalleVentaController {

    //Inyectamos el SERVICIO y NO el repositorio
    //El controlador solo debe tener conexion con el Servicio
    private final IDetalleVentaService detalleVentaService;

    //Como buena practica la Inyeccion de dependencias debe hacerse por el constructor
    public DetalleVentaController(IDetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<DetalleVenta>> listar() {
        List<DetalleVenta> detalles = detalleVentaService.listarTodos();
        return ResponseEntity.ok(detalles);
        //200 OK con la lista de detalles
    }

    //{codigoVenta} es una variable de ruta (venta de la que se quieren ver los detalles)
    @GetMapping("/venta/{codigoVenta}")
    public ResponseEntity<List<DetalleVenta>> listarPorVenta(@PathVariable Long codigoVenta) {
        //@PathVariable: Toma el valor de la URL y lo asigna al parametro codigoVenta
        List<DetalleVenta> detalles = detalleVentaService.listarPorVenta(codigoVenta);
        return ResponseEntity.ok(detalles);
    }

    //{codigo} es una variable de ruta (valor a buscar)
    @GetMapping("/{codigo}")
    public ResponseEntity<DetalleVenta> buscarPorCodigo(@PathVariable Long codigo) {
        //@PathVariable: Toma el valor de la URL y lo asigna al parametro codigo
        return detalleVentaService.buscarPorCodigo(codigo)
                //Si Optional tiene valor, devuelve 200 OK
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear un nuevo detalle de venta
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody DetalleVenta detalleVenta) {
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo DetalleVenta
        //<?> significa "tipo generico", puede ser un DetalleVenta o un String
        //El subtotal se calcula automaticamente en el servicio
        try {
            DetalleVenta nuevoDetalle = detalleVentaService.guardar(detalleVenta);
            //Intentamos guardar el detalle pero puede lanzar IllegalArgumentException
            return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
            //201 CREATED (mas especifico que 200 para la creacion de un recurso)
        } catch (IllegalArgumentException e) {
            //Si hay error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error
        }
    }

    //PUT: actualizar detalle de venta a traves de su codigo
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigo, @RequestBody DetalleVenta detalleVenta) {
        try {
            if (!detalleVentaService.existeCodigo(codigo))
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
            //404 NOT FOUND

            //Actualiza el detalle, puede lanzar una exception
            //El subtotal se recalcula automaticamente en el servicio
            DetalleVenta detalleActualizado = detalleVentaService.actualizar(codigo, detalleVenta);
            return ResponseEntity.ok(detalleActualizado);
            //200 OK con el detalle ya actualizado
        } catch (IllegalArgumentException e) {
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            //Cualquier otro error como: detalle no encontrado, etc.
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //DELETE: elimina un detalle de venta
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigo) {
        //ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try {
            if (!detalleVentaService.existeCodigo(codigo))
                return ResponseEntity.notFound().build();
            //404 si no existe
            detalleVentaService.eliminar(codigo);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }
}