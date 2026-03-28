package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Venta;
import com.gahelrodriguez.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarActivos() {
        return ventaRepository.findByEstado(1L);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarInactivos() {

        return ventaRepository.findByEstado(0L);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorCliente(Long dpiCliente) {
        return ventaRepository.findByClienteDPICliente(dpiCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorUsuario(Long codigoUsuario) {
        return ventaRepository.findByUsuarioCodigoUsuario(codigoUsuario);
    }

    @Override
    public Venta guardar(Venta venta) {
        validarVenta(venta);
        if (venta.getFechaVenta() == null)
            venta.setFechaVenta(LocalDate.now());
        if (venta.getEstado() == null || venta.getEstado() == 0L)
            venta.setEstado(1L);
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigo(Long codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    public Venta actualizar(Long codigoVenta, Venta venta) {
        if (!ventaRepository.existsById(codigoVenta))
            throw new RuntimeException("Venta no encontrada con codigo " + codigoVenta);
        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Long codigoVenta) {
        if (!ventaRepository.existsById(codigoVenta))
            throw new RuntimeException("Venta no encontrada con codigo " + codigoVenta);
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeCodigo(Long codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    private void validarVenta(Venta venta) {
        if (venta.getCliente() == null || venta.getCliente().getDPICliente() == null)
            throw new IllegalArgumentException("La venta debe tener un cliente asignado");

        if (venta.getUsuario() == null || venta.getUsuario().getCodigoUsuario() == null)
            throw new IllegalArgumentException("La venta debe tener un usuario asignado");

        if (venta.getTotal() == null)
            throw new IllegalArgumentException("El total es un campo obligatorio");

        if (venta.getTotal().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El total no puede ser un valor negativo");
    }
}