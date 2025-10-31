package com.deliverytech.delivery.service.CustomerFolder;

import com.deliverytech.delivery.dto.CustomerFolder.CustomerDTO;
import com.deliverytech.delivery.dto.CustomerFolder.CustomerResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerService {
    
    List<CustomerResponseDTO> getAllCustomers();
    CustomerResponseDTO createCustomer(CustomerDTO customerDTO);
    CustomerResponseDTO updateCustomer(Long customerId, CustomerDTO customerDTO);
    CustomerResponseDTO getCustomer(Long customerId);
    void deleteCustomer(Long customerId);
    CustomerResponseDTO searchByEmail(@Valid String email);
}