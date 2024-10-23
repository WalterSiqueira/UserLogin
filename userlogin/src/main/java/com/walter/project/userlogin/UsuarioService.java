package com.walter.project.userlogin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository userRepository;

    public boolean validarUsuario(String email, String senha) {
        Optional<Usuario> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();

            return user.getSenha().equals(senha);
        }
        return false;
    }
}
