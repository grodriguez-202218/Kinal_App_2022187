package com.gahelrodriguez.kinalapp.controller;

import com.gahelrodriguez.kinalapp.entity.Usuario;
import com.gahelrodriguez.kinalapp.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/usuarios")
//Todas las rutas en este controlador deben empezar con /usuarios
public class UsuarioController {

    //Inyectamos el SERVICIO y NO el repositorio
    //El controlador solo debe tener conexion con el Servicio
    private final IUsuarioService usuarioService;

    //Como buena practica la Inyeccion de dependencias debe hacerse por el constructor
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
        //200 OK con la lista de usuarios
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarActivos() {
        List<Usuario> usuarios = usuarioService.listarActivos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<Usuario>> listarInactivos() {
        List<Usuario> usuarios = usuarioService.listarInactivos();
        return ResponseEntity.ok(usuarios);
    }

    //{codigo} es una variable de ruta (valor a buscar)
    @GetMapping("/{codigo}")
    public ResponseEntity<Usuario> buscarPorCodigo(@PathVariable Long codigo) {
        //@PathVariable: Toma el valor de la URL y lo asigna al parametro codigo
        return usuarioService.buscarPorCodigo(codigo)
                //Si Optional tiene valor, devuelve 200 OK
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> buscarPorUsername(@PathVariable String username) {
        //@PathVariable: Toma el valor de la URL y lo asigna al parametro username
        return usuarioService.buscarPorUsername(username)
                //Si Optional tiene valor, devuelve 200 OK
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario) {
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Usuario
        //<?> significa "tipo generico", puede ser un Usuario o un String
        try {
            Usuario nuevoUsuario = usuarioService.guardar(usuario);
            //Intentamos guardar el usuario pero puede lanzar IllegalArgumentException
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
            //201 CREATED (mas especifico que 200 para la creacion de un recurso)
        } catch (IllegalArgumentException e) {
            //Si hay error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error
        }
    }

    //PUT: actualizar usuario a traves de su codigo
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable Long codigo, @RequestBody Usuario usuario) {
        try {
            if (!usuarioService.existeCodigo(codigo))
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
            //404 NOT FOUND

            //Actualiza el usuario, puede lanzar una exception
            Usuario usuarioActualizado = usuarioService.actualizar(codigo, usuario);
            return ResponseEntity.ok(usuarioActualizado);
            //200 OK con el usuario ya actualizado
        } catch (IllegalArgumentException e) {
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            //Cualquier otro error como: usuario no encontrado, etc.
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //DELETE: elimina un usuario
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable Long codigo) {
        //ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try {
            if (!usuarioService.existeCodigo(codigo))
                return ResponseEntity.notFound().build();
            //404 si no existe
            usuarioService.eliminar(codigo);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }
}