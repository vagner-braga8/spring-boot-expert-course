package io.github.cursodsousa.libraryapi.security;

import io.github.cursodsousa.libraryapi.model.Usuario;
import io.github.cursodsousa.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SENHA_PADRAO = "123";

    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        Usuario usuario = usuarioService.obterPorEmail(email);

        if(usuario == null){
            usuario = criarUsuario(email);
        }


        authentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Usuario criarUsuario(String email) {
        Usuario usuario;
        usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setLogin(extrairPrefixoEmail(email));
        usuario.setSenha(SENHA_PADRAO);
        usuario.setRoles(List.of("USUARIO"));
        usuarioService.salvar(usuario);
        return usuario;
    }

    private String extrairPrefixoEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
