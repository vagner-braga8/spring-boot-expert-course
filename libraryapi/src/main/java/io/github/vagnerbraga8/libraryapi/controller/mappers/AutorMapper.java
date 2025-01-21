package io.github.vagnerbraga8.libraryapi.controller.mappers;

import io.github.vagnerbraga8.libraryapi.controller.dto.AutorDTO;
import io.github.vagnerbraga8.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Vai transformar em um compoente spring para possibilitar injeta-lo onde necessário
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);

}
