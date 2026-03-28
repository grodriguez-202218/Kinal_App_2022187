package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.DetalleVenta;
import com.gahelrodriguez.kinalapp.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/*@Service: Registra esta clase como un Bean de Spring.
 * Indica que la clase contiene la logica del negocio.
 */
@Service
/*@Transactional: Todos los metodos seran transaccionales por defecto.
 * Una transaccion garantiza que la operacion ocurre completa o no ocurre.
 */
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    /*DetalleVentaRepository: repositorio para acceder a la BD.
     * Inyectado por constructor (buena practica).
     */
    private final DetalleVentaRepository detalleVentaRepository;

    /*Constructor: Spring inyecta automaticamente el repositorio.
     * @param detalleVentaRepository Repositorio inyectado por Spring
     */
    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    /*listarTodos: Retorna todos los detalles de venta.
     * Equivale a: SELECT * FROM detalle_venta
     */
    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarTodos() {
        return detalleVentaRepository.findAll();
    }

    /*listarPorVenta: Retorna todos los detalles de una venta especifica.
     * @param codigoVenta Codigo de la venta a consultar
     */
    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarPorVenta(Long codigoVenta) {
        return detalleVentaRepository.findByVentaCodigoVenta(codigoVenta);
    }

    /*guardar: Crea un nuevo DetalleVenta.
     * Valida los datos y calcula el subtotal automaticamente.
     * subtotal = cantidad * precioUnitario
     * @param detalleVenta Objeto con los datos del detalle
     */
    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        validarDetalle(detalleVenta);
        /*BigDecimal.valueOf convierte el Long cantidad a BigDecimal
         * para poder multiplicar con precioUnitario (tambien BigDecimal).
         */
        BigDecimal subtotal = detalleVenta.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalleVenta.getCantidad()));
        detalleVenta.setSubtotal(subtotal);
        return detalleVentaRepository.save(detalleVenta);
    }

    /*buscarPorCodigo: Busca un detalle por su codigo.
     * Optional evita NullPointerException si no existe.
     * @param codigoDetalleVenta Codigo del detalle a buscar
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> buscarPorCodigo(Long codigoDetalleVenta) {
        return detalleVentaRepository.findById(codigoDetalleVenta);
    }

    /*actualizar: Actualiza un DetalleVenta existente.
     * Recalcula el subtotal automaticamente.
     * @param codigoDetalleVenta Codigo del detalle a actualizar
     * @param detalleVentaObjeto con los datos nuevos
     */
    @Override
    public DetalleVenta actualizar(Long codigoDetalleVenta, DetalleVenta detalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta))
            throw new RuntimeException("Detalle de venta no encontrado con codigo " + codigoDetalleVenta);

        detalleVenta.setCodigoDetalleVenta(codigoDetalleVenta);
        validarDetalle(detalleVenta);
        /*Recalculamos el subtotal al actualizar.*/
        BigDecimal subtotal = detalleVenta.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalleVenta.getCantidad()));
        detalleVenta.setSubtotal(subtotal);
        return detalleVentaRepository.save(detalleVenta);
    }

    /*eliminar: Elimina un DetalleVenta por su codigo.
     * @param codigoDetalleVenta Codigo del detalle a eliminar
     */
    @Override
    public void eliminar(Long codigoDetalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta))
            throw new RuntimeException("Detalle de venta no encontrado con codigo " + codigoDetalleVenta);
        detalleVentaRepository.deleteById(codigoDetalleVenta);
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existeCodigo(Long codigoDetalleVenta) {
        return detalleVentaRepository.existsById(codigoDetalleVenta);
    }

    /*validarDetalle: Valida las reglas del negocio antes de guardar.
     * Metodo privado, solo accesible dentro de esta clase.
     * @param detalleVenta Detalle a validar
     */
    private void validarDetalle(DetalleVenta detalleVenta) {
        if (detalleVenta.getVenta() == null || detalleVenta.getVenta().getCodigoVenta() == null)
            throw new IllegalArgumentException("El detalle debe tener una venta asignada");

        if (detalleVenta.getProducto() == null || detalleVenta.getProducto().getCodigoProducto() == null)
            throw new IllegalArgumentException("El detalle debe tener un producto asignado");

        /*getCantidad() ahora retorna Long, se verifica con compareTo*/
        if (detalleVenta.getCantidad() == null || detalleVenta.getCantidad() <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");

        if (detalleVenta.getPrecioUnitario() == null)
            throw new IllegalArgumentException("El precio unitario es un campo obligatorio");

        if (detalleVenta.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio unitario no puede ser un valor negativo");
    }
}