package com.library.client.repository;

import com.library.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmail(String email);

    List<Client> findAllByStatus(Client.Status status);
}
