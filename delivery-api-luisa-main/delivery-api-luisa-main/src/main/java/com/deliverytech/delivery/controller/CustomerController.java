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
@Tag(name = "Customere", description = "Customere API")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@Valid @PathVariable("customerId") Long customerId) {
        CustomerResponseDTO customer = customerService.getCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@Valid @PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@Valid @PathVariable("customerId") Long customerId, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerResponseDTO updatedCustomer = customerService.updateCustomer(customerId, customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerResponseDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("email/{email}")
    public ResponseEntity<CustomerResponseDTO> searchCustomerByEmail(@Valid @PathVariable("email") String email) {
        CustomerResponseDTO customer = customerService.searchByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
}