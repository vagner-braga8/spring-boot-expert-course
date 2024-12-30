package io.github.cursodsousa.libraryapi.controller;

import io.github.cursodsousa.libraryapi.model.Client;
import io.github.cursodsousa.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    //O ideal é utilizar DTO, porém o foco didático é em Authorization Server
    public void salvar(@RequestBody Client client){
        clientService.salvar(client);
    }

}
