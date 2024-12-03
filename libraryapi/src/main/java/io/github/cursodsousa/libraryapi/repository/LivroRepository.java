package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Methods

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTituloContaining (String titulo);

    Livro findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByTituloOrPreco (String titulo, BigDecimal preco);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);


    // JPQL -> Referência as entidades e as propriedades

    @Query (" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Query (" select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileros();
}
