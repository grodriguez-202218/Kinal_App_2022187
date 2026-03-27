package com.gahelrodriguez.kinalapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    //@GeneratedValue: La BD genera el valor automaticamente (auto-incremental)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_venta")
    private Long codigoVenta;
    @Column
    //LocalDate: tipo de dato para fechas sin hora (yyyy-MM-dd)
    private LocalDate fechaVenta;
    @Column
    //BigDecimal: tipo de dato para valores monetarios, evita errores de redondeo
    private BigDecimal total;
    @Column
    private int estado;

    /*@ManyToOne: Muchas ventas pueden pertenecer a un Cliente
     *@JoinColumn: Define la columna de la llave foranea en la tabla ventas
     */
    @ManyToOne
    @JoinColumn(name = "Clientes_dpi_cliente")
    private Cliente cliente;

    /*@ManyToOne: Muchas ventas pueden pertenecer a un Usuario
     *@JoinColumn: Define la columna de la llave foranea en la tabla ventas
     */
    @ManyToOne
    @JoinColumn(name = "Usuarios_codigo_usuario")
    private Usuario usuario;

    public Venta() {
    }

    public Venta(LocalDate fechaVenta, BigDecimal total, int estado, Cliente cliente, Usuario usuario) {
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.estado = estado;
        this.cliente = cliente;
        this.usuario = usuario;
    }

    public Long getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(Long codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}