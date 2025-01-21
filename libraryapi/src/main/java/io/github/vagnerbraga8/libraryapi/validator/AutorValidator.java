package io.github.vagnerbraga8.libraryapi.validator;

import io.github.vagnerbraga8.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.vagnerbraga8.libraryapi.model.Autor;
import io.github.vagnerbraga8.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = autorRepository
                .findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if(autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        return autorEncontrado.isPresent() && !autor.getId().equals(autorEncontrado.get().getId());
    }
}
