package com.michaelmartins.dsclients.repositories;

import com.michaelmartins.dsclients.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
