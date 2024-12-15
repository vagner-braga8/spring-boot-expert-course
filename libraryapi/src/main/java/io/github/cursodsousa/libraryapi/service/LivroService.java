package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import io.github.cursodsousa.libraryapi.repository.specs.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro){
       return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar (Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa (String isbn, String nomeAutor, String titulo, GeneroLivro genero, Integer anoPublicacao ) {

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

        return livroRepository.findAll(specs);
    }
}
