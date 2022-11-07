package com.library.client.service;

import com.library.client.model.Client;

import java.util.List;

public interface ClientService {

    Client addClient(Client client);

    List<Client> getClients(Client.Status status);

    Client getClient(Long clientId);

    Client getClientToSend(Long clientId);

    Client putClient(Long clientId, Client client);

    Client patchClient(Long clientId, Client client);

    void deleteClient(Long clientId);
}
