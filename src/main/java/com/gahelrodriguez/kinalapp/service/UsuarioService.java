package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Usuario;
import com.gahelrodriguez.kinalapp.repository.UsuarioRepository;
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
public class UsuarioService implements IUsuarioService {

    /*private: solo accesible dentro de la clase
      UsuarioRepository: Es el repositorio para acceder a la base de datos
      Spring nos da el repositorio mediante Inyeccion de Dependencias
    */
    private final UsuarioRepository usuarioRepository;

    /*Constructor: Se ejecuta al crear el objeto
     *Parametros: Spring pasa el repositorio automaticamente,
     *a esto se le conoce como Inyeccion de Dependencias
     *Asignamos el repositorio a nuestra variable de clase
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //@Override: Indica que estamos implementando un metodo de la interfaz
    @Override
    //readOnly = true: Optimiza la consulta, no bloquea la BD
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
        /*Llama al metodo findAll() del repositorio de Spring Data JPA
         *Este metodo ejecuta: SELECT * FROM usuarios
         */
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarActivos() {
        return usuarioRepository.findByEstado(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarInactivos() {
        return usuarioRepository.findByEstado(0);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        /*Metodo guardar: crea un Usuario
         *Aqui colocamos la logica del negocio antes de guardar
         *Primero validamos el dato
         */
        validarUsuario(usuario);
        if (usuario.getEstado() == 0)
            usuario.setEstado(1);
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCodigo(Long codigoUsuario) {
        //Busca un usuario por su codigo
        return usuarioRepository.findById(codigoUsuario);
        //Optional nos evita el NullPointerException
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        //Busca un usuario por su username
        return usuarioRepository.findByUsername(username);
        //Optional nos evita el NullPointerException
    }

    @Override
    public Usuario actualizar(Long codigoUsuario, Usuario usuario) {
        //Actualiza un usuario existente
        if (!usuarioRepository.existsById(codigoUsuario))
            throw new RuntimeException("Usuario no encontrado con codigo " + codigoUsuario);
        //Si no existe se lanza una exception (error controlado)

        /*1. Asegura que el codigo del objeto coincida con el de la URL
         *2. Por seguridad usamos el codigo de la URL y no el que viene en el JSON
         */
        usuario.setCodigoUsuario(codigoUsuario);
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long codigoUsuario) {
        //Eliminar un usuario
        if (!usuarioRepository.existsById(codigoUsuario))
            throw new RuntimeException("Usuario no encontrado con codigo " + codigoUsuario);
        usuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeCodigo(Long codigoUsuario) {
        //Verificar si existe el usuario
        return usuarioRepository.existsById(codigoUsuario);
        //Retorna true o false
    }

    //Metodo privado: solo puede utilizarse dentro de la clase
    private void validarUsuario(Usuario usuario) {
        /*Validaciones del negocio: Este metodo es privado porque
         *es algo interno del servicio
         */
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty())
            //Si el username es null o esta vacio despues de quitar espacios
            //Lanza una exception con un mensaje
            throw new IllegalArgumentException("El username es un campo obligatorio");

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty())
            throw new IllegalArgumentException("La contrasena es un campo obligatorio");

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty())
            throw new IllegalArgumentException("El email es un campo obligatorio");

        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty())
            throw new IllegalArgumentException("El rol es un campo obligatorio");
    }
}