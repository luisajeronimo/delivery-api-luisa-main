package com.deliverytech.delivery.dto.ClientFolder;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientDTO {

    @NotNull(message = "Client name cannot be null")
    private String name;

    @NotNull(message = "Client email cannot be null")
    private String email;

    @NotNull(message = "Client CPF cannot be null")
    //@Pattern(regexp = "/d{11}/", message = "CPF must be 11 digits")
    private String cpf;

    //@Pattern(regexp = "/d{10}/", message = "Phone must be 10 digits")
    private String phone;
}