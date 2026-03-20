package com.example.envio_email.controller;

import com.example.envio_email.controller.dto.EmailRequest;
import com.example.envio_email.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;


    @GetMapping("/enviar")
    public String enviar(){
        emailService.enviarEmail("${spring.mail.username}",
                "Cabeção",
                "Olá! Este é um e-mail enviado pela API de Eduardo automaticamente.");

        return "Email enviado";
    }

    @GetMapping("/enviar-html")
    public String enviarHtml() throws MessagingException {
        emailService.enviarEmailComTemplate(
                "${spring.mail.username}",
                "Teste com HTML",
                "Bem-vindo!",
                "Este é um e-mail com template HTML usando Thymeleaf, by: dudu"
        );
        return "Email HTML enviado!";
    }

    @GetMapping("/enviar-html-img")
    public String enviarHtmlImg() throws MessagingException {
        emailService.enviarEmailComTemplateImagem(
                "${spring.mail.username}",
                "Teste com HTML",
                "Bem-vindo!",
                "Este é um e-mail com template HTML com imagem usando Thymeleaf, by: dudu"
        );
        return "Email HTML enviado!";
    }

    @PostMapping("/enviar-email")
    public String enviarEmail(@RequestBody EmailRequest request) throws MessagingException {

        emailService.enviarEmailComTemplateImagem(
                request.destinatario(),
                request.assunto(),
                request.titulo(),
                request.mensagem()
        );
        return "Email enviado com sucesso!";
    }
}
