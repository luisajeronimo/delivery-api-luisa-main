package com.deliverytech.delivery.service.ClientFolder;

import com.deliverytech.delivery.dto.ClientFolder.ClientDTO;
import com.deliverytech.delivery.dto.ClientFolder.ClientResponseDTO;
import com.deliverytech.delivery.entity.ClientFolder.Client;
import com.deliverytech.delivery.exception.BusinessException;
import com.deliverytech.delivery.repository.ClientFolder.IClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientService implements IClientService {
    
    @Autowired
    private IClientRepository clientRepository;

    public ClientService() {
        super();
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        ModelMapper modelMapper = new ModelMapper();
        List<Client> clients = clientRepository.findAll();
        return Arrays.asList(modelMapper.map(clients, ClientResponseDTO[].class));
    }

    @Override
    public ClientResponseDTO createClient(ClientDTO clientDTO) {
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + clientDTO.getEmail());
        }
        ModelMapper modelMapper = new ModelMapper();
        Client entity = modelMapper.map(clientDTO, Client.class);
        Client client = clientRepository.save(entity);
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    @Override
    public ClientResponseDTO updateClient(Long clientId, ClientDTO clientDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            return null;
        }
        modelMapper.map(clientDTO, client);
        client = clientRepository.save(client);
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    @Override
    public ClientResponseDTO getClient(Long clientId) {
        ModelMapper modelMapper = new ModelMapper();
        return clientRepository.findById(clientId).map(client -> modelMapper.map(client, ClientResponseDTO.class)).orElse(null);
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    public ClientResponseDTO searchByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        var client = clientRepository.findClientByEmail(email);
        return modelMapper.map(client, ClientResponseDTO.class);
    }
}
