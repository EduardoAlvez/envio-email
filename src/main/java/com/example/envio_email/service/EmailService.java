package com.example.envio_email.service;

import com.example.envio_email.controller.dto.EmailAtivacaoRequest;
import com.example.envio_email.service.exceptions.FalhaEnvioEmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    public static final String ATIVE_SUA_CONTA = "Ative sua conta";
    @Value("${spring.mail.username}")
    private String remetenteFixo;


    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

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


    public void enviarEmailAtivacao(EmailAtivacaoRequest dados){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

            // Configurar o contexto do Thymeleaf
            Context context = new Context();
            context.setVariable("nomeUsuario", dados.getNomeUsuario());
            context.setVariable("linkAtivacao", dados.getLinkAtivacao());
            context.setVariable("validadeHoras", dados.getValidadeHoras());

            String corpoEmail = templateEngine.process("emailAtivacaoTemplate", context);

            mimeMessageHelper.setFrom(remetenteFixo);
            mimeMessageHelper.setTo(dados.getEmailDestino());
            mimeMessageHelper.setSubject(ATIVE_SUA_CONTA);
            mimeMessageHelper.setText(corpoEmail, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new FalhaEnvioEmailException("Erro ao enviar email de ativação", e);
        }

    }
}
