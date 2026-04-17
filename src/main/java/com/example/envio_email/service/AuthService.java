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
        // Validação
        if (usuarioRequest == null) {
            throw new UsuarioNuloException("Usuario nulo.");
        }

        Usuario usuario = UsuarioMapper.toUsuario(usuarioRequest);
        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        // Criar token com tempo personalizado (vindo da requisição)
        int tempoExpiracao = usuarioRequest.tempoExpiracaoHoras() != null ?
                usuarioRequest.tempoExpiracaoHoras() : 24;

        TokenVerificador verificadorToken = new TokenVerificador(usuarioSalvo, tempoExpiracao);

        // Preparar email de ativação
        EmailAtivacaoRequest emailRequest = new EmailAtivacaoRequest();
        emailRequest.setNomeUsuario(usuarioSalvo.getNome());

        // URL completa do link de ativação
        String linkAtivacao = "https://seusite.com/verificarCadastro/" + verificadorToken.getToken();
        emailRequest.setLinkAtivacao(linkAtivacao);

        emailRequest.setValidadeHoras(verificadorToken.getTempo());
        emailRequest.setEmailDestino(usuarioSalvo.getEmail());

        // Configurar URLs do footer (opcional, podem vir da requisição também)
        emailRequest.setUrlPoliticaPrivacidade("https://seusite.com/politica-privacidade");
        emailRequest.setUrlTermosUso("https://seusite.com/termos-uso");
        emailRequest.setUrlCentralAjuda("https://seusite.com/ajuda");

        // Salvar token e enviar email
        tokenVerificadorRepository.save(verificadorToken);
        emailService.enviarEmailAtivacao(emailRequest);

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
