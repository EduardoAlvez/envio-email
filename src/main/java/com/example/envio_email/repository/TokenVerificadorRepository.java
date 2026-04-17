package com.example.envio_email.repository;

import com.example.envio_email.model.TokenVerificador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenVerificadorRepository extends JpaRepository<TokenVerificador, Long> {

    Optional<TokenVerificador> findByToken(String token);
}
