package com.example.envio_email.service;

import com.example.envio_email.controller.dto.UsuarioRequest;
import com.example.envio_email.controller.mapper.UsuarioMapper;
import com.example.envio_email.model.TokenVerificador;
import com.example.envio_email.model.Usuario;
import com.example.envio_email.repository.TokenVerificadorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenVerificadorRepository tokenVerificadorRepository;
    private final UsuarioService usuarioService;
    private final EmailService emailService;

    AuthService(TokenVerificadorRepository tokenVerificadorRepository, UsuarioService usuarioService, EmailService emailService){
        this.tokenVerificadorRepository = tokenVerificadorRepository;
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }

    public String registrar(UsuarioRequest usuarioRequest) {

        if(usuarioRequest == null) return "null";
        Usuario usuario = UsuarioMapper.toUsuario(usuarioRequest);

        TokenVerificador verificadorToken = new TokenVerificador(usuarioService.salvar(usuario));

        tokenVerificadorRepository.save(verificadorToken);
        emailService.enviarEmailVerificacao(usuario, verificadorToken.getToken());

        return "Registrado";
    }
}
