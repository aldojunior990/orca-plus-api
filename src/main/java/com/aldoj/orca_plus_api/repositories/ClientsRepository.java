package com.aldoj.orca_plus_api.repositories;

import com.aldoj.orca_plus_api.domain.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientsRepository extends JpaRepository<Client, UUID> {

    List<Client> findAllByUserId(UUID user_id);
}
