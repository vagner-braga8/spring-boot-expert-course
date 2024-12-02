package io.github.cursodsousa.libraryapi;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryapiApplication {

	public static void main(String[] args) {
		//SpringApplication.run(LibraryapiApplication.class, args);
		var context = SpringApplication.run(LibraryapiApplication.class, args);
		AutorRepository repository = context.getBean(AutorRepository.class);
		exemploSalvarRegistro(repository);
	}

	public static void exemploSalvarRegistro(AutorRepository autorRepository){
		Autor autor = new Autor();
		autor.setNome("José S.");
		autor.setDataNascimento(LocalDate.of(1950,1,1));
		autor.setNacionalidade("Brasileira");

		var autorSalvo = autorRepository.save(autor);
		System.out.println("Autor Salvo:" + autorSalvo);
	}

}
