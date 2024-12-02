package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("João.");
        autor.setDataNascimento(LocalDate.of(1965,1,1));
        autor.setNacionalidade("Brasileira");

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo:" + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("19eea6b4-3f9f-42a8-a53c-69096f07f19d");

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

}
