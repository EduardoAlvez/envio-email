package com.example.envio_email.controller;

import com.example.envio_email.service.EmailService;
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
                "Olá! Este é um e-mail enviado pela API de eduardo automaticamente.");

        return "Email enviado";
    }
}
