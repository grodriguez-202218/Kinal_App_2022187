package com.gahelrodriguez.kinalapp.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    //@GeneratedValue: La BD genera el valor automaticamente
    // (auto-incremental)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_productos")
    private Long codigoProducto;
    @Column
    private String nombreProducto;
    @Column
    //BigDecimal: tipo de dato para valores monetarios o decimales precisos
    //evita errores de redondeo que tendria double o float
    private BigDecimal precio;
    @Column
    private int stock;
    @Column
    private int estado;

    public Producto() {
    }

    public Producto(Long codigoProducto, String nombreProducto, BigDecimal precio, int stock, int estado) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
    }

    public Long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
