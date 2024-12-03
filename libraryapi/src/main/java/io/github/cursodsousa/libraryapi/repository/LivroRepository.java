package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTituloContaining (String titulo);

    Livro findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByTituloOrPreco (String titulo, BigDecimal preco);
}
