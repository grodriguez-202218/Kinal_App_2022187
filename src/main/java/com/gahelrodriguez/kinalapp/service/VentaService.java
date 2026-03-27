package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Venta;
import com.gahelrodriguez.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//Anotacion que registra esta clase como un Bean de Spring
//Indica que la clase implementa la logica del negocio
@Service
//Por defecto todos los metodos de esta clase seran transaccionales
//Una transaccion garantiza que una operacion ocurre completa o no ocurre
@Transactional
public class VentaService implements IVentaService {

    /*private: solo accesible dentro de la clase
      VentaRepository: Es el repositorio para acceder a la base de datos
      Spring nos da el repositorio mediante Inyeccion de Dependencias
    */
    private final VentaRepository ventaRepository;

    /*Constructor: Se ejecuta al crear el objeto
     *Parametros: Spring pasa el repositorio automaticamente,
     *a esto se le conoce como Inyeccion de Dependencias
     *Asignamos el repositorio a nuestra variable de clase
     */
    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    //@Override: Indica que estamos implementando un metodo de la interfaz
    @Override
    //readOnly = true: Optimiza la consulta, no bloquea la BD
    @Transactional(readOnly = true)
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
        /*Llama al metodo findAll() del repositorio de Spring Data JPA
         *Este metodo ejecuta: SELECT * FROM ventas
         */
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarActivos() {
        return ventaRepository.findByEstado(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarInactivos() {
        return ventaRepository.findByEstado(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorCliente(String dpiCliente) {
        //Retorna todas las ventas asociadas al DPI del cliente
        return ventaRepository.findByClienteDPICliente(dpiCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorUsuario(Long codigoUsuario) {
        //Retorna todas las ventas registradas por un usuario especifico
        return ventaRepository.findByUsuarioCodigoUsuario(codigoUsuario);
    }

    @Override
    public Venta guardar(Venta venta) {
        /*Metodo guardar: crea una Venta
         *Aqui colocamos la logica del negocio antes de guardar
         *Primero validamos el dato
         */
        validarVenta(venta);
        //Si no viene fecha, se asigna la fecha actual automaticamente
        if (venta.getFechaVenta() == null)
            venta.setFechaVenta(LocalDate.now());
        if (venta.getEstado() == 0)
            venta.setEstado(1);
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigo(Long codigoVenta) {
        //Busca una venta por su codigo
        return ventaRepository.findById(codigoVenta);
        //Optional nos evita el NullPointerException
    }

    @Override
    public Venta actualizar(Long codigoVenta, Venta venta) {
        //Actualiza una venta existente
        if (!ventaRepository.existsById(codigoVenta))
            throw new RuntimeException("Venta no encontrada con codigo " + codigoVenta);
        //Si no existe se lanza una exception (error controlado)

        /*1. Asegura que el codigo del objeto coincida con el de la URL
         *2. Por seguridad usamos el codigo de la URL y no el que viene en el JSON
         */
        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Long codigoVenta) {
        //Eliminar una venta
        if (!ventaRepository.existsById(codigoVenta))
            throw new RuntimeException("Venta no encontrada con codigo " + codigoVenta);
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeCodigo(Long codigoVenta) {
        //Verificar si existe la venta
        return ventaRepository.existsById(codigoVenta);
        //Retorna true o false
    }

    //Metodo privado: solo puede utilizarse dentro de la clase
    private void validarVenta(Venta venta) {
        /*Validaciones del negocio: Este metodo es privado porque
         *es algo interno del servicio
         */
        if (venta.getCliente() == null || venta.getCliente().getDPICliente() == null
                || venta.getCliente().getDPICliente().trim().isEmpty())
            //Verifica que la venta tenga un cliente asignado
            throw new IllegalArgumentException("La venta debe tener un cliente asignado");

        if (venta.getUsuario() == null || venta.getUsuario().getCodigoUsuario() == null)
            //Verifica que la venta tenga un usuario asignado
            throw new IllegalArgumentException("La venta debe tener un usuario asignado");

        if (venta.getTotal() == null)
            throw new IllegalArgumentException("El total es un campo obligatorio");

        if (venta.getTotal().compareTo(BigDecimal.ZERO) < 0)
            //compareTo: compara dos BigDecimal, retorna negativo si es menor
            throw new IllegalArgumentException("El total no puede ser un valor negativo");
    }
}