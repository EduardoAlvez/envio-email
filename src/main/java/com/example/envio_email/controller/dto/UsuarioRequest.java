package com.example.envio_email.controller.dto;

public record UsuarioRequest(
        String nome,
        int senha,
        String email) {
}
