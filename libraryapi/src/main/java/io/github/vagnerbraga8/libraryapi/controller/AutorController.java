package io.github.vagnerbraga8.libraryapi.controller;

import io.github.vagnerbraga8.libraryapi.controller.dto.AutorDTO;
import io.github.vagnerbraga8.libraryapi.controller.mappers.AutorMapper;
import io.github.vagnerbraga8.libraryapi.model.Autor;
import io.github.vagnerbraga8.libraryapi.service.AutorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
@Tag(name = "Autores")
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Salvar", description = "Cadastrar novo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")

    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        var autor = autorMapper.toEntity(autorDTO);
        autorService.salvar(autor);

        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Obter Detalhes", description = "Retorna os dados do autor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")

    })
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta um autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "400", description = "Autor possui livro cadastrado.")

    })
    public ResponseEntity<Void> deletarPorId(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parâmetros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso.")

    })
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> autorDTOList = resultado
                .stream()
                .map(autorMapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(autorDTOList);

    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar", description = "Atualiza um autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")

    })
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO autorDTO) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autor.setDataNascimento(autorDTO.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}
