package com.deliverytech.delivery.service.CustomerFolder;

import com.deliverytech.delivery.config.exception.BusinessException;
import com.deliverytech.delivery.dto.CustomerFolder.CustomerDTO;
import com.deliverytech.delivery.dto.CustomerFolder.CustomerResponseDTO;
import com.deliverytech.delivery.entity.CustomerFolder.Customer;
import com.deliverytech.delivery.repository.CustomerFolder.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
/**
 * Serviço para gerenciamento de clientes (Customer).
 *
 * Responsabilidades:
 * - CRUD de clientes (criação, atualização, remoção e consulta)
 * - Validações básicas como unicidade de e-mail
 *
 * Observação: utiliza ModelMapper para conversão entre entidade e DTO.
 */
public class CustomerService implements ICustomerService {
    
    @Autowired
    private ICustomerRepository customerRepository;

    public CustomerService() {
        super();
    }

    @Override
    /**
     * Retorna todos os clientes cadastrados, mapeados para DTOs.
     *
     * @return lista de CustomerResponseDTO
     */
    public List<CustomerResponseDTO> getAllCustomers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Customer> customers = customerRepository.findAll();
        return Arrays.asList(modelMapper.map(customers, CustomerResponseDTO[].class));
    }

    @Override
    /**
     * Cria um novo cliente após validar unicidade do e-mail.
     *
     * @param customerDTO dados do cliente a ser criado
     * @return CustomerResponseDTO do cliente criado
     */
    public CustomerResponseDTO createCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + customerDTO.getEmail());
        }
        ModelMapper modelMapper = new ModelMapper();
        Customer entity = modelMapper.map(customerDTO, Customer.class);
        Customer customer = customerRepository.save(entity);
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    @Override
    /**
     * Atualiza os dados de um cliente existente.
     *
     * @param customerId id do cliente
     * @param customerDTO DTO com campos para atualização
     * @return CustomerResponseDTO após atualização ou null se não existir
     */
    public CustomerResponseDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return null;
        }
        modelMapper.map(customerDTO, customer);
        customer = customerRepository.save(customer);
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    @Override
    /**
     * Recupera um cliente por id.
     *
     * @param customerId id do cliente
     * @return CustomerResponseDTO ou null se não encontrado
     */
    public CustomerResponseDTO getCustomer(Long customerId) {
        ModelMapper modelMapper = new ModelMapper();
        return customerRepository.findById(customerId).map(customer -> modelMapper.map(customer, CustomerResponseDTO.class)).orElse(null);
    }

    @Override
    /**
     * Remove um cliente por id.
     *
     * @param customerId id do cliente a ser removido
     */
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    /**
     * Busca um cliente pelo e-mail.
     *
     * @param email e-mail do cliente
     * @return CustomerResponseDTO correspondente
     */
    public CustomerResponseDTO searchByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        var customer = customerRepository.findCustomerByEmail(email);
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }
}
