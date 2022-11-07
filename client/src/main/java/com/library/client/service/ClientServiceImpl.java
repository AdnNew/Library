package com.library.client.service;

import com.library.client.exception.ClientError;
import com.library.client.exception.ClientException;
import com.library.client.model.Client;
import com.library.client.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(Client client) {
        if (clientRepository.existsByEmail(client.getEmail()))
            throw new ClientException(ClientError.CLIENT_IS_ALREADY_EXISTS);
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getClients(Client.Status status) {
        if (status != null)
            return clientRepository.findAllByStatus(status);
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientException(ClientError.CLIENT_IS_NOT_EXISTS));
        return client;
    }

    @Override
    public Client getClientToSend(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientException(ClientError.CLIENT_IS_NOT_EXISTS));
        validateClient(client);
        return client;
    }

    private static void validateClient(Client client) {
        if (Client.Status.Inactive.equals(client.getStatus()))
            throw new ClientException(ClientError.CLIENT_STATUS_IS_ALREADY_INACTIVE);
    }

    @Override
    public Client putClient(Long clientId, Client client) {
        Client clientFromDb = getClient(clientId);
        if (clientRepository.existsByEmail(client.getEmail()) && !(clientFromDb.getEmail().equals(client.getEmail())))
            throw new ClientException(ClientError.CLIENT_EMAIL_IS_NOT_UNAVAILABLE);
        setClient(client, clientFromDb);
        return clientRepository.save(clientFromDb);
    }

    @Override
    public Client patchClient(Long clientId, Client client) {
        Client clientFromDb = getClient(clientId);
        if (!ObjectUtils.isEmpty(client.getFirstName()))
            clientFromDb.setFirstName(client.getFirstName());
        if (!ObjectUtils.isEmpty(client.getLastName()))
            clientFromDb.setLastName(client.getLastName());
        if (!ObjectUtils.isEmpty(client.getStatus()))
            clientFromDb.setStatus(client.getStatus());

        return clientRepository.save(clientFromDb);

    }

    @Override
    public void deleteClient(Long clientId) {
        Client client = getClient(clientId);
        validateClient(client);
        client.setStatus(Client.Status.Inactive);
        clientRepository.save(client);
    }

    private static void setClient(Client client, Client clientFromDb) {
        clientFromDb.setFirstName(client.getFirstName());
        clientFromDb.setLastName(client.getLastName());
        clientFromDb.setEmail(client.getEmail());
        clientFromDb.setStatus(client.getStatus());
    }
}
