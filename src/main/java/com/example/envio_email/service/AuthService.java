package com.example.envio_email.service;

import com.example.envio_email.controller.dto.EmailAtivacaoDTO;
import com.example.envio_email.controller.dto.UsuarioRequest;
import com.example.envio_email.controller.mapper.UsuarioMapper;
import com.example.envio_email.model.TokenVerificador;
import com.example.envio_email.model.Usuario;
import com.example.envio_email.repository.TokenVerificadorRepository;
import com.example.envio_email.service.exceptions.FalhaAtivacaoException;
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

        if (usuarioRequest == null) return "null";
        Usuario usuario = UsuarioMapper.toUsuario(usuarioRequest);

        TokenVerificador verificadorToken = new TokenVerificador(usuarioService.salvar(usuario));

        EmailAtivacaoDTO emailAtivacaoDTO = new EmailAtivacaoDTO();
        emailAtivacaoDTO.setNomeUsuario(usuario.getNome());
        emailAtivacaoDTO.setLinkAtivacao("/verificarCadastro/" + verificadorToken.getToken());
        emailAtivacaoDTO.setValidadeHoras(verificadorToken.getTEMPO());
        emailAtivacaoDTO.setEmailDestino(usuario.getEmail());


        tokenVerificadorRepository.save(verificadorToken);
        emailService.enviarEmailAtivacao(emailAtivacaoDTO);

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
