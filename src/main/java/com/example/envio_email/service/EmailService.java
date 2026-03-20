package com.example.envio_email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String remetenteFixo;

    public void enviarEmail(String destinatario, String assunto, String texto){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(remetenteFixo);
        simpleMailMessage.setTo(destinatario);
        simpleMailMessage.setSubject(assunto);
        simpleMailMessage.setText(texto);
        javaMailSender.send(simpleMailMessage);

    }
}
