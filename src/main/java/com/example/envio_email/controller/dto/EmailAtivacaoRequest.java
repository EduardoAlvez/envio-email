package com.example.envio_email.controller.dto;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

@Component
public class EmailAtivacaoRequest {

    @NotBlank(message = "Nome do usuário é obrigatório")
    private String nomeUsuario;

    @NotBlank(message = "Link de ativação é obrigatório")
    @Pattern(regexp = "^(http|https)://.*$", message = "Link de ativação deve ser uma URL válida")
    private String linkAtivacao;

    @Min(value = 1, message = "Validade deve ser no mínimo 1 hora")
    @Max(value = 168, message = "Validade não pode exceder 168 horas (7 dias)")
    private Integer validadeHoras;

    @NotBlank(message = "Email destino é obrigatório")
    @Email(message = "Email destino deve ser válido")
    private String emailDestino;

    @NotBlank(message = "Link é obrigatório")
    @Pattern(regexp = "^(http|https)://.*$", message = "Link de ativação deve ser uma URL válida")
    private String urlPoliticaPrivacidade;

    @NotBlank(message = "Link é obrigatório")
    @Pattern(regexp = "^(http|https)://.*$", message = "Link de ativação deve ser uma URL válida")
    private String urlTermosUso;

    @NotBlank(message = "Link é obrigatório")
    @Pattern(regexp = "^(http|https)://.*$", message = "Link de ativação deve ser uma URL válida")
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
}
