package io.github.vagnerbraga8.libraryapi.repository.specs;

import io.github.vagnerbraga8.libraryapi.model.GeneroLivro;
import io.github.vagnerbraga8.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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

    // and to_char (data_publicacao, 'YYYY') = :anoPublicacao
    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")),anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT); //Possibilidade de controlar o JOIN
            return cb.like( cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");

            //return cb.like( cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%"); //Sempre INNER JOIN
        };
    }

}
