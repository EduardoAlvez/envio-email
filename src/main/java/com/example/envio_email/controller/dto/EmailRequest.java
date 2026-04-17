package com.example.envio_email.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
        @NotBlank(message = "Email destino é obrigatório")
        @Email(message = "Email destino deve ser válido")
        String destinatario,

        @NotBlank(message = "Assunto é obrigatório.")
        String assunto,

        @NotBlank(message = "O titulo é obrigatório.")
        String titulo,

        @NotBlank(message = "A mensagem é obrigatória.")
        String mensagem) {
}
