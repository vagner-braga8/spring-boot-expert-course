package io.github.cursodsousa.libraryapi.repository.specs;

import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    // where isbn = :isbn
    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    // upper(livro.titulo) like (%:param%)
    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, cb) ->
                cb.like( cb.upper(root.get("titulo")),
                "%" + titulo.toUpperCase() + "%");
    }

    // where genero = :genero
    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }
}
