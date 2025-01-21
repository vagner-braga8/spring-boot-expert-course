package io.github.vagnerbraga8.libraryapi.repository;

import io.github.vagnerbraga8.libraryapi.model.Autor;
import io.github.vagnerbraga8.libraryapi.model.GeneroLivro;
import io.github.vagnerbraga8.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> , JpaSpecificationExecutor<Livro> {


    Page<Livro> findByAutor(Autor autor, Pageable pageable);

    // Query Methods

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTituloContaining (String titulo);

    Livro findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByTituloOrPreco (String titulo, BigDecimal preco);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    Optional<Livro> findByIsbn(String isbn);


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

    //named parameters
    @Query (" select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> buscarPorGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade);

    //positional parameters
    @Query (" select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> buscarPorGeneroPositionalParameters( GeneroLivro generoLivro, String nomePropriedade);

    @Modifying
    @Transactional
    @Query (" delete from Livro where genero = ?1 ")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query (" update Livro set dataPublicacao = ?1 where titulo = ?2")
    void updateDataPublicacao(LocalDate novaData, String titulo);

    boolean existsByAutor(Autor autor);
}
