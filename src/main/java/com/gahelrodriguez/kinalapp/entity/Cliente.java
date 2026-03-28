package com.gahelrodriguez.kinalapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cliente {
    @JsonProperty("DPICliente")
    @Id
    @Column(name = "dpi_cliente")
    private Long DPICliente;
    @Column
    private String nombreCliente;
    @Column
    private String apellidoCliente;
    @Column
    private String direccion;
    @Column
    private Long estado;

    /*Constructor vacio: Requerido por JPA.*/
    public Cliente() {
    }

    public Cliente(Long DPICliente, String nombreCliente, String apellidoCliente,
                   String direccion, Long estado) {
        this.DPICliente = DPICliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.direccion = direccion;
        this.estado = estado;
    }

    public Long getDPICliente() {
        return DPICliente;
    }

    public void setDPICliente(Long DPICliente) {
        this.DPICliente = DPICliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }
}