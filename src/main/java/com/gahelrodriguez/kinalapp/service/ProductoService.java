package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Producto;
import com.gahelrodriguez.kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarActivos() {
        // CORREGIDO: 1L en lugar de 1 (Long, no int)
        return productoRepository.findByEstado(1L);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarInactivos() {
        // CORREGIDO: 0L en lugar de 0 (Long, no int)
        return productoRepository.findByEstado(0L);
    }

    @Override
    public Producto guardar(Producto producto) {
        validarProducto(producto);
        // CORREGIDO: comparacion con Long usando equals() o null-safe check
        if (producto.getEstado() == null || producto.getEstado() == 0L)
            producto.setEstado(1L);
        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorCodigo(Long codigoProducto) {
        return productoRepository.findById(codigoProducto);
    }

    @Override
    public Producto actualizar(Long codigoProducto, Producto producto) {
        if (!productoRepository.existsById(codigoProducto))
            throw new RuntimeException("Producto no encontrado con codigo " + codigoProducto);
        producto.setCodigoProducto(codigoProducto);
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(Long codigoProducto) {
        if (!productoRepository.existsById(codigoProducto))
            throw new RuntimeException("Producto no encontrado con codigo " + codigoProducto);
        productoRepository.deleteById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeCodigo(Long codigoProducto) {
        return productoRepository.existsById(codigoProducto);
    }

    private void validarProducto(Producto producto) {
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty())
            throw new IllegalArgumentException("El nombre del producto es un campo obligatorio");

        if (producto.getPrecio() == null)
            throw new IllegalArgumentException("El precio es un campo obligatorio");

        if (producto.getPrecio().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio no puede ser un valor negativo");

        if (producto.getStock() == null || producto.getStock() < 0L)
            throw new IllegalArgumentException("El stock no puede ser un valor negativo");
    }
}