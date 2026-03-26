package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Producto;
import com.gahelrodriguez.kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//Anotacion que registra esta clase como un Bean de Spring
//Indica que la clase implementa la logica del negocio
@Service
//Por defecto todos los metodos de esta clase seran transaccionales
//Una transaccion garantiza que una operacion ocurre completa o no ocurre
@Transactional
public class ProductoService implements IProductoService {

    /*private: solo accesible dentro de la clase
      ProductoRepository: Es el repositorio para acceder a la base de datos
      Spring nos da el repositorio mediante Inyeccion de Dependencias
    */
    private final ProductoRepository productoRepository;

    /*Constructor: Se ejecuta al crear el objeto
     *Parametros: Spring pasa el repositorio automaticamente,
     *a esto se le conoce como Inyeccion de Dependencias
     *Asignamos el repositorio a nuestra variable de clase
     */
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //@Override: Indica que estamos implementando un metodo de la interfaz
    @Override
    //readOnly = true: Optimiza la consulta, no bloquea la BD
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
        /*Llama al metodo findAll() del repositorio de Spring Data JPA
         *Este metodo ejecuta: SELECT * FROM productos
         */
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarActivos() {
        return productoRepository.findByEstado(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarInactivos() {
        return productoRepository.findByEstado(0);
    }

    @Override
    public Producto guardar(Producto producto) {
        /*Metodo guardar: crea un Producto
         *Aqui colocamos la logica del negocio antes de guardar
         *Primero validamos el dato
         */
        validarProducto(producto);
        if (producto.getEstado() == 0)
            producto.setEstado(1);
        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorCodigo(Long codigoProducto) {
        //Busca un producto por su codigo
        return productoRepository.findById(codigoProducto);
        //Optional nos evita el NullPointerException
    }

    @Override
    public Producto actualizar(Long codigoProducto, Producto producto) {
        //Actualiza un producto existente
        if (!productoRepository.existsById(codigoProducto))
            throw new RuntimeException("Producto no encontrado con codigo " + codigoProducto);
        //Si no existe se lanza una exception (error controlado)

        /*1. Asegura que el codigo del objeto coincida con el de la URL
         *2. Por seguridad usamos el codigo de la URL y no el que viene en el JSON
         */
        producto.setCodigoProducto(codigoProducto);
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(Long codigoProducto) {
        //Eliminar un producto
        if (!productoRepository.existsById(codigoProducto))
            throw new RuntimeException("Producto no encontrado con codigo " + codigoProducto);
        productoRepository.deleteById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeCodigo(Long codigoProducto) {
        //Verificar si existe el producto
        return productoRepository.existsById(codigoProducto);
        //Retorna true o false
    }

    //Metodo privado: solo puede utilizarse dentro de la clase
    private void validarProducto(Producto producto) {
        /*Validaciones del negocio: Este metodo es privado porque
         *es algo interno del servicio
         */
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty())
            //Si el nombre es null o esta vacio despues de quitar espacios
            //Lanza una exception con un mensaje
            throw new IllegalArgumentException("El nombre del producto es un campo obligatorio");

        if (producto.getPrecio() == null)
            throw new IllegalArgumentException("El precio es un campo obligatorio");

        if (producto.getPrecio().compareTo(BigDecimal.ZERO) < 0)
            //compareTo: compara dos BigDecimal, retorna negativo si es menor
            throw new IllegalArgumentException("El precio no puede ser un valor negativo");

        if (producto.getStock() < 0)
            throw new IllegalArgumentException("El stock no puede ser un valor negativo");
    }
}