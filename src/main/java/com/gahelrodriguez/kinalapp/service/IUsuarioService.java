package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    //Interfaz: Es un contrato que dice QUÉ métodos debe tener
    //cualquier servicio de Usuarios. No tiene
    //implementación, solo la definición de los métodos

    //Metodo que devuelve una lista de todos los usuarios
    List<Usuario> listarTodos();
    //List<Usuario> devuelve una lista
    //de objetos de la entidad Usuario

    //Metodos que devuelven listas filtradas por estado
    List<Usuario> listarActivos();
    List<Usuario> listarInactivos();

    //Metodo que guarda un Usuario en la BD
    //Parametros: Recibe un objeto Usuario con los datos a guardar
    Usuario guardar(Usuario usuario);

    //Optional: Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Usuario> buscarPorCodigo(Long codigoUsuario);
    Optional<Usuario> buscarPorUsername(String username);

    //Metodo que actualiza un Usuario
    //Parametros: codigoUsuario del Usuario a actualizar
    //            usuario: Objeto con los datos nuevos
    //Retorna un objeto de tipo Usuario ya actualizado
    Usuario actualizar(Long codigoUsuario, Usuario usuario);

    //void: no retorna ningun dato
    //Elimina un Usuario por su codigo
    void eliminar(Long codigoUsuario);

    //boolean: Retorna true si existe, false si no existe
    boolean existeCodigo(Long codigoUsuario);
}