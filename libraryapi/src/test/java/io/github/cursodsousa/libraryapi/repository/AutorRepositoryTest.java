package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria B.");
        autor.setDataNascimento(LocalDate.of(1970,1,20));
        autor.setNacionalidade("Brasileira");

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo:" + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("decad5bf-6938-4cb2-878b-adbebf55e83a");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()) {

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor:");
            System.out.println(possivelAutor.get());

            autorEncontrado.setNome("Abílio L.");

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void todosAutoresTest(){
        List<Autor> autores = repository.findAll();
        System.out.println("###    -= Todos autores =-    ###");
        for (Autor autor: autores) {
            System.out.println(autor.getNome());
        }
    }
    
    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("19eea6b4-3f9f-42a8-a53c-69096f07f19d");
        repository.deleteById(id);
        System.out.println("Usuário deletado por id com sucesso!");
    }

    @Test
    public void delete(){
        var id = UUID.fromString("decad5bf-6938-4cb2-878b-adbebf55e83a");
        var autor = repository.findById(id).get();
        repository.delete(autor);
        System.out.println("Usuário deletado com sucesso!");
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Jhon T.");
        autor.setDataNascimento(LocalDate.of(1979,6,21));
        autor.setNacionalidade("Americano");

        Livro livroCleanCode = new Livro();
        livroCleanCode.setIsbn("22222-01");
        livroCleanCode.setPreco(BigDecimal.valueOf(283));
        livroCleanCode.setGenero(GeneroLivro.CIENCIA);
        livroCleanCode.setTitulo("Clean Code");
        livroCleanCode.setDataPublicacao(LocalDate.of(2004,10,7));
        livroCleanCode.setAutor(autor);

        Livro livroThePragmaticProgrammer = new Livro();
        livroThePragmaticProgrammer.setIsbn("33333-01");
        livroThePragmaticProgrammer.setPreco(BigDecimal.valueOf(417));
        livroThePragmaticProgrammer.setGenero(GeneroLivro.CIENCIA);
        livroThePragmaticProgrammer.setTitulo("The Pragmatic Programmer");
        livroThePragmaticProgrammer.setDataPublicacao(LocalDate.of(2010,10,31));
        livroThePragmaticProgrammer.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livroCleanCode);
        autor.getLivros().add(livroThePragmaticProgrammer);

        repository.save(autor);
        //livroRepository.saveAll(autor.getLivros());   *Se não usar o 'CASCADE' na hora de atribuir LIVRO ao AUTOR

    }

}
