package com.pt.clientes.database.repository;

import com.pt.clientes.database.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByTokenIdAndEstado(UUID tokenId, Boolean estado);
}
