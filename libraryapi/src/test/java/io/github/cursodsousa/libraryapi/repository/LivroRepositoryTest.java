package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("90845-11");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = autorRepository
                .findById(UUID.fromString("3e567a27-6966-4032-9d4f-6cde838a390e")).
                orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }


    @Test
    public void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("00000-01");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("BTC");
        livro.setDataPublicacao(LocalDate.of(2008,10,31));


        Autor autor = new Autor();
        autor.setNome("Satoshi Nakamoto");
        autor.setDataNascimento(LocalDate.of(1945,1,20));
        autor.setNacionalidade("Matrix");

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    public void salvarAutorELivroSemCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("00000-02");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("BTC II");
        livro.setDataPublicacao(LocalDate.of(2008,10,31));


        Autor autor = new Autor();
        autor.setNome("Putin");
        autor.setDataNascimento(LocalDate.of(1965,1,20));
        autor.setNacionalidade("Russa");

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    public void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("c1f82f71-a71a-48c0-ae81-a0f2f7ef6d00");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID autorId = UUID.fromString("59e1039e-7632-4f60-833e-4ece606e4ace");
        Autor maria = autorRepository.findById(autorId).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);
    }

}