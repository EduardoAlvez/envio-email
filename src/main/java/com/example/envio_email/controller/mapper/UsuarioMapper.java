package com.example.envio_email.controller.mapper;

import com.example.envio_email.controller.dto.UsuarioRequest;
import com.example.envio_email.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioRequest usuarioRequest){
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.nome());
        usuario.setEmail(usuarioRequest.email());
        usuario.setSenha(usuarioRequest.senha());

        return usuario;
    }
}
