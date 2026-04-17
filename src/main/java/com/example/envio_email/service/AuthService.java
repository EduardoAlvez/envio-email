package com.example.envio_email.service;

import com.example.envio_email.controller.dto.EmailAtivacaoRequest;
import com.example.envio_email.controller.dto.UsuarioRequest;
import com.example.envio_email.controller.mapper.UsuarioMapper;
import com.example.envio_email.model.TokenVerificador;
import com.example.envio_email.model.Usuario;
import com.example.envio_email.repository.TokenVerificadorRepository;
import com.example.envio_email.service.exceptions.FalhaAtivacaoException;
import com.example.envio_email.service.exceptions.UsuarioNuloException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final TokenVerificadorRepository tokenVerificadorRepository;
    private final UsuarioService usuarioService;
    private final EmailService emailService;

    AuthService(TokenVerificadorRepository tokenVerificadorRepository, UsuarioService usuarioService, EmailService emailService) {
        this.tokenVerificadorRepository = tokenVerificadorRepository;
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }

    public String registrar(UsuarioRequest usuarioRequest) {

        if (usuarioRequest == null) throw new UsuarioNuloException("Usuario nulo.");
        Usuario usuario = UsuarioMapper.toUsuario(usuarioRequest);

        TokenVerificador verificadorToken = new TokenVerificador(usuarioService.salvar(usuario));

        EmailAtivacaoRequest emailAtivacaoRequest = new EmailAtivacaoRequest();
        emailAtivacaoRequest.setNomeUsuario(usuario.getNome());
        emailAtivacaoRequest.setLinkAtivacao("/verificarCadastro/" + verificadorToken.getToken());
        emailAtivacaoRequest.setValidadeHoras(verificadorToken.getTEMPO());
        emailAtivacaoRequest.setEmailDestino(usuario.getEmail());


        tokenVerificadorRepository.save(verificadorToken);
        emailService.enviarEmailAtivacao(emailAtivacaoRequest);

        return verificadorToken.getToken();
    }

    public String ativarUsuario(String token) {
        Optional<TokenVerificador> tokenVerificador = Optional.ofNullable(tokenVerificadorRepository.findByToken(token)
                .orElseThrow(FalhaAtivacaoException::new));

        if (tokenVerificador.isEmpty()) return "Token nulo.";
        TokenVerificador tokenVerificadorOk = tokenVerificador.get();

        if (tokenVerificadorOk.isExpired()) return "Token expirado.";
        if (tokenVerificadorOk.getUsuario().isEnabled()) return "Usuário já está atívo.";

        Usuario usuarioAutorizado = tokenVerificadorOk.getUsuario();
        usuarioAutorizado.setEnabled(true);

        usuarioService.salvar(usuarioAutorizado);
        return "Ok";
    }
}
