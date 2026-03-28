package com.gahelrodriguez.kinalapp.repository;

import com.gahelrodriguez.kinalapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByEstado(Long estado);

    List<Venta> findByClienteDPICliente(Long dpiCliente);

    List<Venta> findByUsuarioCodigoUsuario(Long codigoUsuario);
}