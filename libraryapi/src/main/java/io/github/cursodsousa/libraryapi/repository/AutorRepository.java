package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    @Query("SELECT a FROM Autor a WHERE " +
            "LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%')) " +
            "AND LOWER(a.nacionalidade) LIKE LOWER(CONCAT('%', :nacionalidade, '%'))")
    List<Autor> pesquisar(@Param("nome") String nome, @Param("nacionalidade") String nacionalidade);

}
