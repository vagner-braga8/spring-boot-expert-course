package io.github.vagnerbraga8.libraryapi.security;

import io.github.vagnerbraga8.libraryapi.model.Usuario;
import io.github.vagnerbraga8.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof CustomAuthentication customAuthentication) {
            return customAuthentication.getUsuario();
        }
        return null;
    }
}
