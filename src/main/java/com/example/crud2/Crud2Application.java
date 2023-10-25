package com.example.crud2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud2.backend.model.Pessoa;
import com.example.crud2.backend.repositories.PessoaRepository;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RestController
public class Crud2Application {

	private static final Logger log = LoggerFactory.getLogger(Crud2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Crud2Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(PessoaRepository repository) {
		return (args) -> {
			// salvando as pessoas
			repository.save(new Pessoa("Kauã", 17));
			repository.save(new Pessoa("Isaac", 17));

			// selecionando as pessoas
			log.info("pessoas:");
			log.info("----------------------------------------------------");
			for (Pessoa pessoa : repository.findAll()) {
				log.info(pessoa.toString());
			}
			log.info("");
		};
	}

}
