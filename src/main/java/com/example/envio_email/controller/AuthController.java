package com.example.envio_email.controller;

import com.example.envio_email.controller.dto.UsuarioRequest;
import com.example.envio_email.service.AuthService;
import com.example.envio_email.service.exceptions.FalhaAtivacaoException;
import com.example.envio_email.service.exceptions.UsuarioNuloException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;

    }

    @PostMapping(value = "/resgitroUsuario")
    public ResponseEntity<String> cadastrarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(authService.registrar(usuarioRequest));
        }catch (UsuarioNuloException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário nulo.");
        }
    }


    @GetMapping(value = "/verificarCadastro/{token}")
    public ResponseEntity<String> verificarRegistro(@PathVariable("token") String token){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(authService.ativarUsuario(token));
        }catch (FalhaAtivacaoException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha na ativação");
        }
    }
}
