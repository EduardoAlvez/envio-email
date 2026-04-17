package com.example.envio_email.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
        @NotBlank(message = "Nome do usuário é obrigatório")
        String nome,

        @NotBlank(message = "Senha é obrigatória.")
        //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "A senha não atende os criterios.")
        Integer senha,

        @NotBlank(message = "Email destino é obrigatório")
        @Email(message = "Email destino deve ser válido")
        String email) {
}
