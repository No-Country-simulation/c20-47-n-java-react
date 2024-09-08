package com.FlowBanck.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @NotEmpty(message = "Por favor, introduzca una dirección de correo electrónico válida")
    @Email
    private String email;

    @NotEmpty(message = "Por favor, introduzca su contraseña")
    private String password;
}
