package com.example.envio_email.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(
        name = "EmailAtivacaoRequest",
        description = "DTO para requisição de envio de email de ativação de conta",
        example = """
        {
            "nomeUsuario": "João Silva",
            "emailDestino": "joao.silva@email.com",
            "linkAtivacao": "https://meusite.com/ativar/abc-123-def",
            "validadeHoras": 24,
            "urlPoliticaPrivacidade": "https://meusite.com/politica",
            "urlTermosUso": "https://meusite.com/termos",
            "urlCentralAjuda": "https://meusite.com/ajuda"
        }
        """)
public class EmailAtivacaoRequest {

    @NotBlank(message = "Nome do usuário é obrigatório")
    private String nomeUsuario;

    @NotBlank(message = "Link de ativação é obrigatório")
    @Pattern(regexp = "^(http|https)://.*$", message = "Link de ativação deve ser uma URL válida")
    private String linkAtivacao;

    @NotNull(message = "Validade em horas é obrigatória")
    @Min(value = 1, message = "Validade deve ser no mínimo 1 hora")
    @Max(value = 168, message = "Validade não pode exceder 168 horas (7 dias)")
    private Integer validadeHoras;

    @NotBlank(message = "Email destino é obrigatório")
    @Email(message = "Email destino deve ser válido")
    private String emailDestino;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL da política de privacidade deve ser válida")
    private String urlPoliticaPrivacidade;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL dos termos de uso deve ser válida")
    private String urlTermosUso;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL da central de ajuda deve ser válida")
    private String urlCentralAjuda;

    // Construtores, Getters e Setters
    public EmailAtivacaoRequest() {}

    public EmailAtivacaoRequest(String nomeUsuario, String linkAtivacao, Integer validadeHoras, String emailDestino) {
        this.nomeUsuario = nomeUsuario;
        this.linkAtivacao = linkAtivacao;
        this.validadeHoras = validadeHoras;
        this.emailDestino = emailDestino;
        this.urlPoliticaPrivacidade = "https://seusite.com/politica-privacidade";
        this.urlTermosUso = "https://seusite.com/termos-uso";
        this.urlCentralAjuda = "https://seusite.com/ajuda";
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getLinkAtivacao() {
        return linkAtivacao;
    }

    public void setLinkAtivacao(String linkAtivacao) {
        this.linkAtivacao = linkAtivacao;
    }

    public Integer getValidadeHoras() {
        return validadeHoras;
    }

    public void setValidadeHoras(Integer validadeHoras) {
        this.validadeHoras = validadeHoras;
    }

    public String getUrlPoliticaPrivacidade() {
        return urlPoliticaPrivacidade;
    }

    public void setUrlPoliticaPrivacidade(String urlPoliticaPrivacidade) {
        this.urlPoliticaPrivacidade = urlPoliticaPrivacidade;
    }

    public String getUrlTermosUso() {
        return urlTermosUso;
    }

    public void setUrlTermosUso(String urlTermosUso) {
        this.urlTermosUso = urlTermosUso;
    }

    public String getUrlCentralAjuda() {
        return urlCentralAjuda;
    }

    public void setUrlCentralAjuda(String urlCentralAjuda) {
        this.urlCentralAjuda = urlCentralAjuda;
    }

    @Override
    public String toString() {
        return "EmailAtivacaoRequest{" +
                "nomeUsuario='" + nomeUsuario + '\'' +
                ", emailDestino='" + emailDestino + '\'' +
                ", validadeHoras=" + validadeHoras +
                ", linkAtivacao='[PROTECTED]'" +
                '}';
    }
}
