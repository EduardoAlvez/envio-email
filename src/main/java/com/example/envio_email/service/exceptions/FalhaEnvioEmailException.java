package com.example.envio_email.service.exceptions;

import jakarta.mail.MessagingException;

public class FalhaEnvioEmailException extends RuntimeException {
    public FalhaEnvioEmailException(String s, MessagingException e) {
    }
}
