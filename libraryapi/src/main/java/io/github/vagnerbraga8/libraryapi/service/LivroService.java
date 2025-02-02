package io.github.vagnerbraga8.libraryapi.service;

import io.github.vagnerbraga8.libraryapi.model.GeneroLivro;
import io.github.vagnerbraga8.libraryapi.model.Livro;
import io.github.vagnerbraga8.libraryapi.model.Usuario;
import io.github.vagnerbraga8.libraryapi.repository.LivroRepository;
import io.github.vagnerbraga8.libraryapi.repository.specs.LivroSpecs;
import io.github.vagnerbraga8.libraryapi.security.SecurityService;
import io.github.vagnerbraga8.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro){
        livroValidator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar (Livro livro) {
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisa (
            String isbn,
            String nomeAutor,
            String titulo,
            GeneroLivro genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina) {

        // select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());
        if(isbn != null){
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and((LivroSpecs.tituloLike(titulo)));
        }

        if(genero != null){
            specs = specs.and((LivroSpecs.generoEqual(genero)));
        }

        if(anoPublicacao != null){
            specs = specs.and(LivroSpecs.anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null) {
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return livroRepository.findAll(specs, pageRequest);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base.");
        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
