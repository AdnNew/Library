package com.library.client.controller;

import com.library.client.model.Client;
import com.library.client.service.ClientService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    Client addClient(@RequestBody @Valid Client client) {
        return clientService.addClient(client);
    }

    @GetMapping
    List<Client> getClients(@RequestParam (required = false) Client.Status status) {
        return clientService.getClients(status);
    }

    @GetMapping("/{clientId}")
    Client getClient(@PathVariable Long clientId) {
        return clientService.getClient(clientId);
    }

    @GetMapping("/get/{clientId}")
    Client getClientToSend(@PathVariable Long clientId) {
        return clientService.getClientToSend(clientId);
    }

    @PutMapping("/{clientId}")
    Client putClient(@PathVariable Long clientId, @RequestBody @Valid Client client) {
        return clientService.putClient(clientId, client);
    }

    @PatchMapping("/{clientId}")
    Client patchClient(@PathVariable Long clientId, @RequestBody Client client) {
        return clientService.patchClient(clientId, client);
    }

    @DeleteMapping("/{clientId}")
    void deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
    }

}
