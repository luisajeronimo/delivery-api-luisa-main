package com.deliverytech.delivery.config.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Segurança: evitar mapear id vindo do DTO para a entidade quando criando novos registros
        // Protege contra atualizações acidentais quando o cliente enviar um id por engano.
        mapper.typeMap(com.deliverytech.delivery.dto.ProductFolder.ProductDTO.class,
                com.deliverytech.delivery.entity.ProductFolder.Product.class)
            .addMappings(m -> m.skip(com.deliverytech.delivery.entity.ProductFolder.Product::setId));

        return mapper;
    }
}