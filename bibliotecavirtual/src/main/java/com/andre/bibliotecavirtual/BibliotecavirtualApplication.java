package com.andre.bibliotecavirtual;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andre.bibliotecavirtual.domain.Usuario;
import com.andre.bibliotecavirtual.repositories.UsuarioRepository;

@SpringBootApplication
public class BibliotecavirtualApplication implements CommandLineRunner{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(BibliotecavirtualApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {		
		Usuario u1 = new Usuario(null, "Andre Gomes", "andre", "123");
		Usuario u2 = new Usuario(null, "Valdir Cezar", "valdir", "123");
		
		usuarioRepository.saveAll(Arrays.asList(u1, u2));
	}

}