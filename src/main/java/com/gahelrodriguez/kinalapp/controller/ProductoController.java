package com.gahelrodriguez.kinalapp.controller;

import com.gahelrodriguez.kinalapp.entity.Producto;
import com.gahelrodriguez.kinalapp.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/productos")
//Todas las rutas en este controlador deben empezar con /productos
public class ProductoController {

    //Inyectamos el SERVICIO y NO el repositorio
    //El controlador solo debe tener conexion con el Servicio
    private final IProductoService productoService;

    //Como buena practica la Inyeccion de dependencias debe hacerse por el constructor
    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
        //200 OK con la lista de productos
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> listarActivos() {
        List<Producto> productos = productoService.listarActivos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<Producto>> listarInactivos() {
        List<Producto> productos = productoService.listarInactivos();
        return ResponseEntity.ok(productos);
    }

    //{codigo} es una variable de ruta (valor a buscar)
    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> buscarPorCodigo(@PathVariable Long codigo) {
        //@PathVariable: Toma el valor de la URL y lo asigna al parametro codigo
        return productoService.buscarPorCodigo(codigo)
                //Si Optional tiene valor, devuelve 200 OK
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear un nuevo producto
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Producto producto) {
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Producto
        //<?> significa "tipo generico", puede ser un Producto o un String
        try {
            Producto nuevoProducto = productoService.guardar(producto);
            //Intentamos guardar el producto pero puede lanzar IllegalArgumentException
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
            //201 CREATED (mas especifico que 200 para la creacion de un recurso)
        } catch (IllegalArgumentException e) {
            //Si hay error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error
        }
    }

    //PUT: actualizar producto a traves de su codigo
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigo, @RequestBody Producto producto) {
        try {
            if (!productoService.existeCodigo(codigo))
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
            //404 NOT FOUND

            //Actualiza el producto, puede lanzar una exception
            Producto productoActualizado = productoService.actualizar(codigo, producto);
            return ResponseEntity.ok(productoActualizado);
            //200 OK con el producto ya actualizado
        } catch (IllegalArgumentException e) {
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            //Cualquier otro error como: producto no encontrado, etc.
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //DELETE: elimina un producto
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigo) {
        //ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try {
            if (!productoService.existeCodigo(codigo))
                return ResponseEntity.notFound().build();
            //404 si no existe
            productoService.eliminar(codigo);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }
}