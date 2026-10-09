package com.pt.clientes.database.repository;

import com.pt.clientes.database.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByTokenId(UUID tokenId);

    @Modifying
    @Query("""
                DELETE FROM Cliente c
                WHERE c.persona.tokenId = :tokenId
            """)
    void deleteByPersonaId(@Param("tokenId") UUID tokenId);
}
