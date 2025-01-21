package io.github.vagnerbraga8.libraryapi.controller.mappers;

import io.github.vagnerbraga8.libraryapi.controller.dto.UsuarioDTO;
import io.github.vagnerbraga8.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO usuarioDTO);

}
