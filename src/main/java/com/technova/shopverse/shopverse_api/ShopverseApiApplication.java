package com.technova.shopverse.shopverse_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopverseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopverseApiApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner initData(ProductRepository prodr, CategoryRepository catr) {
		return args -> {
			System.out.println("=== Se comienza sin datos para realizar pruebas HTTPCODE ===");
			// ===== CARGA INICIAL =====

			// ===== DATE =====
			LocalDate dateNow = LocalDate.now();
			LocalDate dateTom = dateNow.plusDays(1);
			LocalDate dateFu1 = dateNow.plusYears(5);
			LocalDate dateFu2 = dateTom.plusYears(6);

			// Añadir Spring Boot Starter Security
		};
	}*/

}
