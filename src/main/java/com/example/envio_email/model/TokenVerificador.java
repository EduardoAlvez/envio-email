package com.example.envio_email.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class TokenVerificador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime expiryDate;
    private final int TEMPO = 24;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public TokenVerificador() {}

    public TokenVerificador(Usuario usuario) {
        this.usuario = usuario;
        this.token = UUID.randomUUID().toString();
        this.expiryDate = LocalDateTime.now().plusHours(TEMPO); // expira em 24h
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public String getToken() {
        return token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Long getId() {
        return id;
    }

    public int getTEMPO() {
        return TEMPO;
    }
}
