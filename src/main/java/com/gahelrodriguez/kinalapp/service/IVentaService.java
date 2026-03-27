package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Venta;

import java.util.List;
import java.util.Optional;

public interface IVentaService {
    //Interfaz: Es un contrato que dice QUÉ métodos debe tener
    //cualquier servicio de Ventas. No tiene
    //implementación, solo la definición de los métodos

    //Metodo que devuelve una lista de todas las ventas
    List<Venta> listarTodos();
    //List<Venta> devuelve una lista
    //de objetos de la entidad Venta

    //Metodos que devuelven listas filtradas por estado
    List<Venta> listarActivos();
    List<Venta> listarInactivos();

    //Metodo que devuelve todas las ventas de un cliente por su DPI
    List<Venta> listarPorCliente(String dpiCliente);

    //Metodo que devuelve todas las ventas registradas por un usuario
    List<Venta> listarPorUsuario(Long codigoUsuario);

    //Metodo que guarda una Venta en la BD
    //Parametros: Recibe un objeto Venta con los datos a guardar
    Venta guardar(Venta venta);

    //Optional: Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Venta> buscarPorCodigo(Long codigoVenta);

    //Metodo que actualiza una Venta
    //Parametros: codigoVenta de la Venta a actualizar
    //            venta: Objeto con los datos nuevos
    //Retorna un objeto de tipo Venta ya actualizado
    Venta actualizar(Long codigoVenta, Venta venta);

    //void: no retorna ningun dato
    //Elimina una Venta por su codigo
    void eliminar(Long codigoVenta);

    //boolean: Retorna true si existe, false si no existe
    boolean existeCodigo(Long codigoVenta);
}