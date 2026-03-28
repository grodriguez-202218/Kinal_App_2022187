package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {
    //Interfaz: Es un contrato que dice QUÉ métodos debe tener
    //cualquier servicio de DetalleVenta. No tiene
    //implementación, solo la definición de los métodos

    //Metodo que devuelve una lista de todos los detalles
    List<DetalleVenta> listarTodos();
    //List<DetalleVenta> devuelve una lista
    //de objetos de la entidad DetalleVenta

    //Metodo que devuelve todos los detalles de una venta especifica
    //Parametros: codigoVenta de la venta a consultar
    List<DetalleVenta> listarPorVenta(Long codigoVenta);

    //Metodo que guarda un DetalleVenta en la BD
    //Parametros: Recibe un objeto DetalleVenta con los datos a guardar
    //El subtotal se calcula automaticamente en el servicio
    DetalleVenta guardar(DetalleVenta detalleVenta);

    //Optional: Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<DetalleVenta> buscarPorCodigo(Long codigoDetalleVenta);

    //Metodo que actualiza un DetalleVenta
    //Parametros: codigoDetalleVenta del detalle a actualizar
    //            detalleVenta: Objeto con los datos nuevos
    //Retorna un objeto de tipo DetalleVenta ya actualizado
    DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVenta);

    //void: no retorna ningun dato
    //Elimina un DetalleVenta por su codigo
    void eliminar(Long codigoDetalleVenta);

    //boolean: Retorna true si existe, false si no existe
    boolean existeCodigo(Long codigoDetalleVenta);
}