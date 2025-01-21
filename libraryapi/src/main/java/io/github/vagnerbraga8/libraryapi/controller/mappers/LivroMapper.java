package io.github.vagnerbraga8.libraryapi.controller.mappers;

import io.github.vagnerbraga8.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.vagnerbraga8.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.vagnerbraga8.libraryapi.model.Livro;
import io.github.vagnerbraga8.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

//uses = AutorMapper.class = 'Dizendo' para o LivroMapper, usar o AutorMapper dentro dos mapeamentos quando necessário.
@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
