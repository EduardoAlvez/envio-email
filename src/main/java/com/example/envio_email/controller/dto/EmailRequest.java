package com.example.envio_email.controller.dto;

public record EmailRequest(String destinatario, String assunto, String titulo, String mensagem) {
}
