package com.gahelrodriguez.kinalapp.service;

import com.gahelrodriguez.kinalapp.entity.Usuario;
import com.gahelrodriguez.kinalapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarActivos() {
        return usuarioRepository.findByEstado(1L);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarInactivos() {
        return usuarioRepository.findByEstado(0L);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        validarUsuario(usuario);
        if (usuario.getEstado() == null || usuario.getEstado() == 0L)
            usuario.setEstado(1L);
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCodigo(Long codigoUsuario) {
        return usuarioRepository.findById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Usuario actualizar(Long codigoUsuario, Usuario usuario) {
        if (!usuarioRepository.existsById(codigoUsuario))
            throw new RuntimeException("Usuario no encontrado con codigo " + codigoUsuario);
        usuario.setCodigoUsuario(codigoUsuario);
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long codigoUsuario) {
        if (!usuarioRepository.existsById(codigoUsuario))
            throw new RuntimeException("Usuario no encontrado con codigo " + codigoUsuario);
        usuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeCodigo(Long codigoUsuario) {
        return usuarioRepository.existsById(codigoUsuario);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty())
            throw new IllegalArgumentException("El username es un campo obligatorio");

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty())
            throw new IllegalArgumentException("La contrasena es un campo obligatorio");

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty())
            throw new IllegalArgumentException("El email es un campo obligatorio");

        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty())
            throw new IllegalArgumentException("El rol es un campo obligatorio");
    }
}