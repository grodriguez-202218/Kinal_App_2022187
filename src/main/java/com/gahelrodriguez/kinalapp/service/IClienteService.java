package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    //Interfaz: Es un contrato que dice QUÉ métodos debe tener
    //cualquier servicio de Clientes. No tiene
    //implementación, solo la definición de los métodos

    //Metodo que devuelve una lista de todos los clientes
    List<Cliente> listarTodos();
    //List<Cliente> devuelve una lista
    //de objetos de la entidad Cliente

    //Metodos que devuelven listas filtradas por estado
    List<Cliente> listarActivos();
    List<Cliente> listarInactivos();

    //Metodo que guarda un Cliente en la BD
    //Parametros: Recibe un objeto Cliente con los datos a guardar
    Cliente guardar(Cliente cliente);

    //Optional: Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Cliente> busacarPorDPI(String dpi);

    //Metodo que actualiza un Cliente
    //Parametros: dpi del Cliente a actualizar
    //            cliente: Objeto con los datos nuevos
    //Retorna un objeto de tipo Cliente ya actualizado
    Cliente actualizar(String dpi, Cliente cliente);

    //void: no retorna ningun dato
    //Elimina un Cliente por su DPI
    void eliminar(String dpi);

    //boolean: Retorna true si existe, false si no existe
    boolean exiteDPI(String dpi);
}