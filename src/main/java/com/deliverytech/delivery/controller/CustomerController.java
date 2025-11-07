package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.CustomerFolder.CustomerDTO;
import com.deliverytech.delivery.dto.CustomerFolder.CustomerResponseDTO;
import com.deliverytech.delivery.service.CustomerFolder.ICustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer", description = "Customer API")
/**
 * CustomerController
 *
 * Responsabilidades:
 * - Expõe endpoints CRUD para clientes (Customer).
 * - Valida entrada via DTOs e delega toda a lógica para {@link com.deliverytech.delivery.service.CustomerFolder.ICustomerService}.
 *
 * Observações de fluxo:
 * - Métodos GET devolvem DTOs de resposta para não expor entidades de persistência.
 * - Métodos que alteram estado (POST, PUT, DELETE) lançam exceções customizadas tratadas por
 *   {@link com.deliverytech.delivery.config.exception.GlobalExceptionHandler} para respostas padronizadas.
 */
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    /**
     * GET /api/v1/customers
     * Retorna todos os clientes registrados.
     *
     * @return 200 OK com lista de {@link com.deliverytech.delivery.dto.CustomerFolder.CustomerResponseDTO}
     */
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{customerId}")
    /**
     * GET /api/v1/customers/{customerId}
     * Busca um cliente pelo ID.
     *
     * @param customerId id do cliente
     * @return 200 OK com {@link com.deliverytech.delivery.dto.CustomerFolder.CustomerResponseDTO}
     */
    public ResponseEntity<CustomerResponseDTO> getCustomer(@Valid @PathVariable("customerId") Long customerId) {
        CustomerResponseDTO customer = customerService.getCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{customerId}")
    /**
     * DELETE /api/v1/customers/{customerId}
     * Remove um cliente por ID.
     *
     * @param customerId id do cliente
     * @return 204 No Content quando removido com sucesso
     */
    public ResponseEntity<Void> deleteCustomer(@Valid @PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{customerId}")
    /**
     * PUT /api/v1/customers/{customerId}
     * Atualiza dados do cliente.
     *
     * @param customerId id do cliente
     * @param customerDTO DTO com campos atualizáveis
     * @return 200 OK com {@link com.deliverytech.delivery.dto.CustomerFolder.CustomerResponseDTO} atualizado
     */
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@Valid @PathVariable("customerId") Long customerId, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerResponseDTO updatedCustomer = customerService.updateCustomer(customerId, customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    /**
     * POST /api/v1/customers
     * Cria um novo cliente a partir do payload.
     *
     * @param customerDTO DTO com dados do cliente
     * @return 201 Created com {@link com.deliverytech.delivery.dto.CustomerFolder.CustomerResponseDTO}
     */
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerResponseDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("email/{email}")
    /**
     * GET /api/v1/customers/email/{email}
     * Busca cliente por e-mail.
     *
     * @param email e-mail do cliente
     * @return 200 OK com {@link com.deliverytech.delivery.dto.CustomerFolder.CustomerResponseDTO}
     */
    public ResponseEntity<CustomerResponseDTO> searchCustomerByEmail(@Valid @PathVariable("email") String email) {
        CustomerResponseDTO customer = customerService.searchByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
}