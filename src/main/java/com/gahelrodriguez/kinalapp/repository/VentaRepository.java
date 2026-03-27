package com.gahelrodriguez.kinalapp.repository;

import com.gahelrodriguez.kinalapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Consulta derivada: Spring genera automaticamente el SQL
    // equivalente a: SELECT * FROM ventas WHERE estado = ?
    List<Venta> findByEstado(int estado);
    // Busca todas las ventas de un cliente especifico por su DPI
    List<Venta> findByClienteDPICliente(String dpiCliente);
    // Busca todas las ventas registradas por un usuario especifico
    List<Venta> findByUsuarioCodigoUsuario(Long codigoUsuario);
}