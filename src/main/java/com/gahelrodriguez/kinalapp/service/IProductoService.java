package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    //Interfaz: Es un contrato que dice QUÉ métodos debe tener
    //cualquier servicio de Productos. No tiene
    //implementación, solo la definición de los métodos

    //Metodo que devuelve una lista de todos los productos
    List<Producto> listarTodos();
    //List<Producto> devuelve una lista
    //de objetos de la entidad Producto

    //Metodos que devuelven listas filtradas por estado
    List<Producto> listarActivos();
    List<Producto> listarInactivos();

    //Metodo que guarda un Producto en la BD
    //Parametros: Recibe un objeto Producto con los datos a guardar
    Producto guardar(Producto producto);

    //Optional: Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Producto> buscarPorCodigo(Long codigoProducto);

    //Metodo que actualiza un Producto
    //Parametros: codigoProducto del Producto a actualizar
    //            producto: Objeto con los datos nuevos
    //Retorna un objeto de tipo Producto ya actualizado
    Producto actualizar(Long codigoProducto, Producto producto);

    //void: no retorna ningun dato
    //Elimina un Producto por su codigo
    void eliminar(Long codigoProducto);

    //boolean: Retorna true si existe, false si no existe
    boolean existeCodigo(Long codigoProducto);
}