package com.example.envio_email.repository;

import com.example.envio_email.model.TokenVerificador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenVerificadorRepository extends JpaRepository<TokenVerificador, Long> {

    TokenVerificador findByToken(String token);
}
