package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Cliente;
import com.gahelrodriguez.kinalapp.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*@Service: Registra esta clase como un Bean de Spring.
 * Indica que la clase contiene la logica del negocio.
 */
@Service
/*@Transactional: Todos los metodos seran transaccionales por defecto.*/
@Transactional
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;

    /*Constructor: Spring inyecta automaticamente el repositorio.*/
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /*listarTodos: Retorna todos los clientes.
     * Equivale a: SELECT * FROM clientes
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /*listarActivos: Retorna clientes con estado=1.*/
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarActivos() {
        return clienteRepository.findByEstado(1L);
    }

    /*listarInactivos: Retorna clientes con estado=0.*/
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarInactivos() {
        return clienteRepository.findByEstado(0L);
    }

    /*guardar: Crea un nuevo Cliente.
     * Valida los datos y asigna estado=1 si no se envia.
     * @param cliente Objeto con los datos del cliente
     */
    @Override
    public Cliente guardar(Cliente cliente) {
        validarCliente(cliente);
        /*Si estado es null o 0, se asigna 1 (activo) por defecto.*/
        if (cliente.getEstado() == null || cliente.getEstado() == 0)
            cliente.setEstado(1L);
        return clienteRepository.save(cliente);
    }

    /*busacarPorDPI: Busca un cliente por su DPI.
     * Optional evita NullPointerException si no existe.
     * @param dpi DPI del cliente a buscar
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> busacarPorDPI(Long dpi) {
        return clienteRepository.findById(dpi);
    }

    /*actualizar: Actualiza un cliente existente.
     * @param dpi     DPI del cliente a actualizar
     * @param cliente Objeto con los datos nuevos
     */
    @Override
    public Cliente actualizar(Long dpi, Cliente cliente) {
        if (!clienteRepository.existsById(dpi))
            throw new RuntimeException("Cliente no encontrado con DPI " + dpi);
        cliente.setDPICliente(dpi);
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    /*eliminar: Elimina un cliente por su DPI.
     * @param dpi DPI del cliente a eliminar
     */
    @Override
    public void eliminar(Long dpi) {
        if (!clienteRepository.existsById(dpi))
            throw new RuntimeException("El cliente no se encontro con el DPI " + dpi);
        clienteRepository.deleteById(dpi);
    }

    /*exiteDPI: Verifica si existe un cliente con ese DPI.
     * @param dpi DPI a verificar
     * @return true si existe, false si no
     */
    @Override
    @Transactional(readOnly = true)
    public boolean exiteDPI(Long dpi) {
        return clienteRepository.existsById(dpi);
    }

    /*validarCliente: Valida las reglas del negocio.
     * Metodo privado, solo accesible dentro de esta clase.
     */
    private void validarCliente(Cliente cliente) {
        if (cliente.getDPICliente() == null)
            throw new IllegalArgumentException("El DPI es un dato obligatorio");

        if (cliente.getNombreCliente() == null || cliente.getNombreCliente().trim().isEmpty())
            throw new IllegalArgumentException("El nombre es un campo obligatorio");

        if (cliente.getApellidoCliente() == null || cliente.getApellidoCliente().trim().isEmpty())
            throw new IllegalArgumentException("El apellido es un campo obligatorio");
    }
}