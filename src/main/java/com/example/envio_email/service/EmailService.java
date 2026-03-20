package com.example.envio_email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String remetenteFixo;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public void enviarEmail(String destinatario, String assunto, String mensagem){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(remetenteFixo);
        simpleMailMessage.setTo(destinatario);
        simpleMailMessage.setSubject(assunto);
        simpleMailMessage.setText(mensagem);
        javaMailSender.send(simpleMailMessage);
    }

    public void enviarEmailComTemplate(String destinatario, String assunto, String titulo, String mensagem) throws MessagingException {
        Context context = new Context();
        context.setVariable("titulo", titulo);
        context.setVariable("mensagem", mensagem);

        String corpoEmail = templateEngine.process("emailTemplate2", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(remetenteFixo);
        mimeMessageHelper.setTo(destinatario);
        mimeMessageHelper.setSubject(assunto);
        mimeMessageHelper.setText(corpoEmail, true);

        javaMailSender.send(mimeMessage);
    }

    public void enviarEmailComTemplateImagem(String destinatario, String assunto, String titulo, String mensagem) throws MessagingException {
        Context context = new Context();
        context.setVariable("titulo", titulo);
        context.setVariable("mensagem", mensagem);
        context.setVariable("logoUrl", "https://i.imgur.com/ebUmVMW.png");

        String corpoEmail = templateEngine.process("emailTemplate3", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(remetenteFixo);
        mimeMessageHelper.setTo(destinatario);
        mimeMessageHelper.setSubject(assunto);
        mimeMessageHelper.setText(corpoEmail, true);

        javaMailSender.send(mimeMessage);
    }
}
