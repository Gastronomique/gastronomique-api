package com.ifpr.gastronomique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GastronomiqueApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GastronomiqueApiApplication.class, args);
		System.out.println("User: admin, Senha: " + new BCryptPasswordEncoder().encode("admin"));
	}

}
