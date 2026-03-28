package com.gahelrodriguez.kinalapp.repository;

import com.gahelrodriguez.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByEstado(Long estado);

    Optional<Usuario> findByUsername(String username);
}