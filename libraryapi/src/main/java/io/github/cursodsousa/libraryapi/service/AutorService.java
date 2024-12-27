package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.Usuario;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import io.github.cursodsousa.libraryapi.security.SecurityService;
import io.github.cursodsousa.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if(autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base.");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido um excluir autor que possui " +
                    " cadastrados!");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        return autorRepository.pesquisar(nome, nacionalidade);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues() //ignorar os valores nulos
                .withIgnoreCase() //ignorar se ta minúsculo ou maiúsculo o valor inputado
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        //.withIgnorePaths("id", "dataNascimento") **Exemplo caso quisesse ignorar atributos específicos do objeto

        Example<Autor> autorExample = Example.of(autor, exampleMatcher);

        return autorRepository.findAll(autorExample);
    }
}
