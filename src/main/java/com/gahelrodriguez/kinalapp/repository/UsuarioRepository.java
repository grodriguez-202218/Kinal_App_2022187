package com.gahelrodriguez.kinalapp.repository;

import com.gahelrodriguez.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Consulta derivada: Spring genera automaticamente el SQL
    // equivalente a: SELECT * FROM usuarios WHERE estado = ?
    List<Usuario> findByEstado(int estado);
    // Busca un usuario por su username
    // Optional evita el NullPointerException si no existe
    Optional<Usuario> findByUsername(String username);
}