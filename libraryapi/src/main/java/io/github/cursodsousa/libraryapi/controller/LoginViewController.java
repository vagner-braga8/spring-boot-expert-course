package io.github.cursodsousa.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//RestController é para API , requisições Rest com Json por exemplo
@Controller //Para páginas webs
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }
}
