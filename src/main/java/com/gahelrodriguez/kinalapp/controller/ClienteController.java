package com.gahelrodriguez.kinalapp.controller;

import com.gahelrodriguez.kinalapp.entity.Cliente;
import com.gahelrodriguez.kinalapp.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/clientes")
//Todas las rutas en este controlador deben empezar con /clientes
public class ClienteController {

    //Inyectamos el SERVICIO y NO el repositorio
    //El controlador solo debe tener conexion con el Servicio
    private final IClienteService clienteService;

    //Como buena practica la Inyeccion de dependencias debe hacerse por el constructor
    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
        //200 OK con la lista de clientes
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> listarActivos() {
        List<Cliente> clientes = clienteService.listarActivos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<Cliente>> listarInactivos() {
        List<Cliente> clientes = clienteService.listarInactivos();
        return ResponseEntity.ok(clientes);
    }

    //{dpi} es una variable de ruta (valor a buscar)
    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi) {
        //@PathVariable: Toma el valor de la URL y lo asigna al parametro dpi
        return clienteService.busacarPorDPI(dpi)
                //Si Optional tiene valor, devuelve 200 OK
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Cliente
        //<?> significa "tipo generico", puede ser un Cliente o un String
        try {
            Cliente nuevoCliente = clienteService.guardar(cliente);
            //Intentamos guardar el cliente pero puede lanzar IllegalArgumentException
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
            //201 CREATED (mas especifico que 200 para la creacion de un recurso)
        } catch (IllegalArgumentException e) {
            //Si hay error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error
        }
    }

    //DELETE: elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi) {
        //ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try {
            if (!clienteService.exiteDPI(dpi))
                return ResponseEntity.notFound().build();
            //404 si no existe
            clienteService.eliminar(dpi);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //PUT: actualizar cliente a traves de DPI
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente) {
        try {
            if (!clienteService.exiteDPI(dpi))
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
            //404 NOT FOUND

            //Actualiza el cliente, puede lanzar una exception
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
            //200 OK con el cliente ya actualizado
        } catch (IllegalArgumentException e) {
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            //Cualquier otro error como: cliente no encontrado, etc.
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }
}