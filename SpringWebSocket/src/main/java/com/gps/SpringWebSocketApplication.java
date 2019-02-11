	package com.gps;

import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gps.model.Usuario;
import com.gps.repository.UsuarioRepository;

@SpringBootApplication
public class SpringWebSocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebSocketApplication.class, args);
	}
	
	/**
	 * Insere alguns registros ao inicalizar o sistema
	 * @param repository
	 * @return
	 */
	@Bean
    ApplicationRunner init(UsuarioRepository repository) {
        return args -> {
            Stream.of("Joao Pedro", "Ana Maria").forEach(name -> {
                Usuario usu = new Usuario();
                usu.setNome(name);
                usu.setEmail(name.replaceAll(" ", "").toLowerCase() + "@gmail.com");
                usu.setSenha("123");
                usu.setSenha(usu.senhaCriptografada());
                repository.save(usu);
            });
        };
    }
}
