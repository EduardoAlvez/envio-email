package com.example.envio_email.service;

import com.example.envio_email.controller.dto.UsuarioRequest;
import com.example.envio_email.controller.mapper.UsuarioMapper;
import com.example.envio_email.model.Usuario;
import com.example.envio_email.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public String salvar(UsuarioRequest usuarioRequest){
        usuarioRepository.save(UsuarioMapper.toUsuario(usuarioRequest));
        return "Salvo.";
    }

    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}
