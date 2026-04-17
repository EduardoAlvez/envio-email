package com.example.envio_email.controller;

import com.example.envio_email.controller.dto.EmailAtivacaoRequest;
import com.example.envio_email.controller.dto.EmailRequest;
import com.example.envio_email.service.EmailService;
import com.example.envio_email.service.exceptions.FalhaEnvioEmailException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email/v1")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/enviar-email")
    public ResponseEntity<String> enviarEmail(@RequestBody EmailRequest request) throws MessagingException {

        emailService.enviarEmailComTemplateImagem(
                request.destinatario(),
                request.assunto(),
                request.titulo(),
                request.mensagem()
        );
        return ResponseEntity.status(HttpStatus.OK).body("Email HTML com imagem enviado!");
    }

    @PostMapping("/enviar-html-verificacao")
    public ResponseEntity<String> enviarEmailValidacao(@RequestBody EmailAtivacaoRequest dados){

        try{
            emailService.enviarEmailAtivacao(dados);
            return ResponseEntity.status(HttpStatus.OK).body("Email de validação enviado com sucesso!");
        }catch (FalhaEnvioEmailException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }


    //APRENDIZADO
/*

    @GetMapping("/enviar")
    public ResponseEntity<String> enviar(){
        emailService.enviarEmail("${spring.mail.username}",
                "Cabeção",
                "Olá! Este é um e-mail enviado pela API de Eduardo automaticamente.");

        return ResponseEntity.status(HttpStatus.OK).body("Email enviado");
    }

    @GetMapping("/enviar-html")
    public ResponseEntity<String> enviarHtml() throws MessagingException {
        emailService.enviarEmailComTemplate(
                "${spring.mail.username}",
                "Teste com HTML",
                "Bem-vindo!",
                "Este é um e-mail com template HTML usando Thymeleaf, by: dudu"
        );
        return ResponseEntity.status(HttpStatus.OK).body("Email HTML enviado!");
    }

    @GetMapping("/enviar-html-img")
    public ResponseEntity<String> enviarHtmlImg() throws MessagingException {
        emailService.enviarEmailComTemplateImagem(
                "${spring.mail.username}",
                "Teste com HTML",
                "Bem-vindo!",
                "Este é um e-mail com template HTML com imagem usando Thymeleaf, by: dudu"
        );
        return ResponseEntity.status(HttpStatus.OK).body("Email HTML com imagem enviado!");
    }
*/

}
