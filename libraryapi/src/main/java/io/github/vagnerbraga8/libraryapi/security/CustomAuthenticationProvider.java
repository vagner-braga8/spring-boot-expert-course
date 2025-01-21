package io.github.vagnerbraga8.libraryapi.security;

import io.github.vagnerbraga8.libraryapi.model.Usuario;
import io.github.vagnerbraga8.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String login = authentication.getName();
       String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuario = usuarioService.obterPorLogin(login);

        if(usuario == null) {
            throw getErroUsuarioNaoEncontrado();
        }

        String senhaCriptografada = usuario.getSenha();

        boolean senhasCoincidem = passwordEncoder.matches(senhaDigitada, senhaCriptografada);

        if(senhasCoincidem){
            return new CustomAuthentication(usuario);
        }

        throw getErroUsuarioNaoEncontrado();
    }

    private UsernameNotFoundException getErroUsuarioNaoEncontrado() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
