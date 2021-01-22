package com.compasso.uol.gabriel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ClientManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientManagerApplication.class, args);
	}

}
