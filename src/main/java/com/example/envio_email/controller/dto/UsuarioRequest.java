package com.example.envio_email.controller.dto;

import jakarta.validation.constraints.*;

public record UsuarioRequest(
        @NotBlank(message = "Nome do usuário é obrigatório")
        @Size(min = 6, max = 50, message = "Nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotBlank(message = "Senha é obrigatória.")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        Integer senha,

        @NotBlank(message = "Email destino é obrigatório")
        @Email(message = "Email destino deve ser válido")
        String email,

        @Min(value = 1, message = "Tempo de expiração deve ser no mínimo 1 hora")
        @Max(value = 168, message = "Tempo de expiração não pode exceder 168 horas (7 dias)")
        Integer tempoExpiracaoHoras) {
}
