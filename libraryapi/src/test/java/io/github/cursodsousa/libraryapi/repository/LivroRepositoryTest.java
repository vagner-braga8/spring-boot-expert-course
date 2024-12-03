package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    @Test
    @Transactional //Abrir a transação para carregar os dados a mais que precisar. (Atributos de relacionamento com FetchType.LAZY
    public void buscarLivroTest(){
        UUID id = UUID.fromString("c1f82f71-a71a-48c0-ae81-a0f2f7ef6d00");
        var livro = repository.findById(id).orElse(null);
        System.out.println("Livro: " + livro.getTitulo());

        //Se usar o fetch = FetchType.LAZY no atributo autor do relacionamento em livro, para trazer é necessário usar o @Transactional
        System.out.println("Autor: " + livro.getAutor().getNome());
    }

    @Test
    public void buscarLivroPorTituloTest(){
        List<Livro> livroList = repository.findByTituloContaining("BTC");
        livroList.forEach(System.out::println);
    }

    @Test
    public void buscarLivroPorTituloEPrecoTest(){
        Livro livro = repository.findByTituloAndPreco("BTC", BigDecimal.valueOf(100));
        System.out.println("Livro: " + livro);
    }

    @Test
    public void buscarLivroPorTituloOuPrecoTest(){
        List<Livro> livroList = repository.findByTituloOrPreco("BTC", null);
        livroList.forEach(System.out::println);
    }

    @Test
    void listarLivrosPorDataInicioEDataFim(){
        List<Livro> livroList = repository.findByDataPublicacaoBetween(LocalDate.of(2008,10,31), LocalDate.of(2008,11,10));
        livroList.forEach(System.out::println);
    }

    @Test
    void listarTodos(){
        List<Livro> livroList = repository.listarTodosOrdenadoPorTituloAndPreco();
        livroList.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        List<Autor> autorList = repository.listarAutoresDosLivros();
        autorList.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParam(){
        List<Livro> livroList = repository.buscarPorGenero(GeneroLivro.CIENCIA, "preco");
        livroList.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamPositionalParameters(){
        List<Livro> livroList = repository.buscarPorGeneroPositionalParameters(GeneroLivro.CIENCIA, "preco");
        livroList.forEach(System.out::println);
    }

}