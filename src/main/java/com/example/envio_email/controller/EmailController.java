package com.example.envio_email.controller;

import com.example.envio_email.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;


    @GetMapping("/enviar")
    public String enviar(){
        emailService.enviarEmail("eduardo.alves@dcx.ufpb.br",
                "Cabeção",
                "Olá! Este é um e-mail enviado pela API de Eduardo automaticamente.");

        return "Email enviado";
    }

    @GetMapping("/enviar-html")
    public String enviarHtml() throws MessagingException {
        emailService.enviarEmailComTemplate(
                "eduardo.alves@dcx.ufpb.br",
                "Teste com HTML",
                "Bem-vindo!",
                "Este é um e-mail com template HTML usando Thymeleaf, by: dudu"
        );
        return "Email HTML enviado!";
    }
}
