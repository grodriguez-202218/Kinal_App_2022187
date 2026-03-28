package com.gahelrodriguez.kinalapp.repository;

import com.gahelrodriguez.kinalapp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByEstado(Long estado);
}