package com.example.envio_email.controller;

import com.example.envio_email.controller.dto.UsuarioRequest;
import com.example.envio_email.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;

    }

    @PostMapping(value = "/resgitroUsuario")
    public String cadastrarUsuario(UsuarioRequest usuarioRequest){
        authService.registrar(usuarioRequest);

        return "OK";
    }


    @GetMapping(value = "/verificarCadastro/{uuid}")
    public String verificarRegistro(@PathVariable("uuid") String uuid){

        return "";
    }
}
