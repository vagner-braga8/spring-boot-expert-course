package io.github.vagnerbraga8.libraryapi.repository;

import io.github.vagnerbraga8.libraryapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByClientId(String id);
}

