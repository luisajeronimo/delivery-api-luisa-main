package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.ClientFolder.ClientDTO;
import com.deliverytech.delivery.dto.ClientFolder.ClientResponseDTO;
import com.deliverytech.delivery.service.ClientFolder.IClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name = "Cliente", description = "Cliente API")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponseDTO> getClient(@Valid @PathVariable("clientId") Long clientId) {
        ClientResponseDTO client = clientService.getClient(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@Valid @PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{clientId}")
    public ResponseEntity<ClientResponseDTO> updateClient(@Valid @PathVariable("clientId") Long clientId, @Valid @RequestBody ClientDTO clientDTO) {
        ClientResponseDTO updatedClient = clientService.updateClient(clientId, clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedClient);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        ClientResponseDTO createdClient = clientService.createClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("email/{email}")
    public ResponseEntity<ClientResponseDTO> searchClientByEmail(@Valid @PathVariable("email") String email) {
        ClientResponseDTO client = clientService.searchByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }
}