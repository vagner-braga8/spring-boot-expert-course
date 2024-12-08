package io.github.cursodsousa.libraryapi.controller;

import io.github.cursodsousa.libraryapi.controller.dto.AutorDTO;
import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.service.AutorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        var autorEntidade = autor.mapearParaAutor();
        autorService.salvar(autorEntidade);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if(autorOptional.isPresent()){
            Autor autorEntidade = autorOptional.get(); //Recebendo a entidade que está dentro do Optional
            AutorDTO autorDTO = new AutorDTO(
                    autorEntidade.getId(),
                    autorEntidade.getNome(),
                    autorEntidade.getDataNascimento(),
                    autorEntidade.getNacionalidade());
            return ResponseEntity.ok(autorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            //required = false , não é obrigatório a passagem do parâmetro.
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

         List<Autor> resultado = autorService.pesquisa(nome, nacionalidade);
         List<AutorDTO> autorDTOList = resultado
                 .stream()
                 .map(autor -> new AutorDTO(
                 autor.getId(),
                 autor.getNome(),
                 autor.getDataNascimento(),
                 autor.getNacionalidade()
         )).collect(Collectors.toList());

        return ResponseEntity.ok(autorDTOList);

    }
}
