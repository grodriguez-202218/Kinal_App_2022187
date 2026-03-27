package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Cliente;
import com.gahelrodriguez.kinalapp.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Anotacion que registra esta clase como un Bean de Spring
//Indica que la clase implementa la logica del negocio
@Service
//Por defecto todos los metodos de esta clase seran transaccionales
//Una transaccion garantiza que una operacion ocurre completa o no ocurre
@Transactional
public class ClienteService implements IClienteService {

    /*private: solo accesible dentro de la clase
      ClienteRepository: Es el repositorio para acceder a la base de datos
      Spring nos da el repositorio mediante Inyeccion de Dependencias
    */
    private final ClienteRepository clienteRepository;

    /*Constructor: Se ejecuta al crear el objeto
     *Parametros: Spring pasa el repositorio automaticamente,
     *a esto se le conoce como Inyeccion de Dependencias
     *Asignamos el repositorio a nuestra variable de clase
     */
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //@Override: Indica que estamos implementando un metodo de la interfaz
    @Override
    //readOnly = true: Optimiza la consulta, no bloquea la BD
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
        /*Llama al metodo findAll() del repositorio de Spring Data JPA
         *Este metodo ejecuta: SELECT * FROM clientes
         */
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarActivos() {
        return clienteRepository.findByEstado(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarInactivos() {
        return clienteRepository.findByEstado(0);
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        /*Metodo guardar: crea un Cliente
         *Aqui colocamos la logica del negocio antes de guardar
         *Primero validamos el dato
         */
        validarCliente(cliente);
        if (cliente.getEstado() == 0)
            cliente.setEstado(1);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> busacarPorDPI(String dpi) {
        //Busca un cliente por DPI
        return clienteRepository.findById(dpi);
        //Optional nos evita el NullPointerException
    }

    @Override
    public Cliente actualizar(String dpi, Cliente cliente) {
        //Actualiza un cliente existente
        if (!clienteRepository.existsById(dpi))
            throw new RuntimeException("Cliente no encontrado con DPI " + dpi);
        //Si no existe se lanza una exception (error controlado)

        /*1. Asegura que el DPI del objeto coincida con el de la URL
         *2. Por seguridad usamos el DPI de la URL y no el que viene en el JSON
         */
        cliente.setDPICliente(dpi);
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(String dpi) {
        //Eliminar un cliente
        if (!clienteRepository.existsById(dpi))
            throw new RuntimeException("El cliente no se encontro con el DPI " + dpi);
        clienteRepository.deleteById(dpi);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exiteDPI(String dpi) {
        //Verificar si existe el cliente
        return clienteRepository.existsById(dpi);
        //Retorna true o false
    }

    //Metodo privado: solo puede utilizarse dentro de la clase
    private void validarCliente(Cliente cliente) {
        /*Validaciones del negocio: Este metodo es privado porque
         *es algo interno del servicio
         */
        if (cliente.getDPICliente() == null || cliente.getDPICliente().trim().isEmpty())
            //Si el DPI es null o esta vacio despues de quitar espacios
            //Lanza una exception con un mensaje
            throw new IllegalArgumentException("El DPI es un dato obligatorio");

        if (cliente.getNombreCliente() == null || cliente.getNombreCliente().trim().isEmpty())
            throw new IllegalArgumentException("El nombre es un campo obligatorio");

        if (cliente.getApellidoCliente() == null || cliente.getApellidoCliente().trim().isEmpty())
            throw new IllegalArgumentException("El apellido es un campo obligatorio");
    }
}