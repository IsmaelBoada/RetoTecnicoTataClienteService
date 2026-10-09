package com.pt.clientes.database.repository;

import com.pt.clientes.database.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {


    Optional<Persona> findByTokenId(UUID idToken);

    Optional<Persona> findByIdentificacion(String identificacion);
}
