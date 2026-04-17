package com.example.envio_email.controller.dto;

public class EmailAtivacaoDTO {
    private String nomeUsuario;
    private String linkAtivacao;
    private Integer validadeHoras;
    private String emailDestino;
    private String urlPoliticaPrivacidade;
    private String urlTermosUso;
    private String urlCentralAjuda;

    // Construtores, Getters e Setters
    public EmailAtivacaoDTO() {}

    public EmailAtivacaoDTO(String nomeUsuario, String linkAtivacao, Integer validadeHoras, String emailDestino) {
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
