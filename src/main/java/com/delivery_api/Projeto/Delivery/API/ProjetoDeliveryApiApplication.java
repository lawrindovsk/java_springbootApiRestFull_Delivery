package com.delivery_api.Projeto.Delivery.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//http://localhost:8080/swagger-ui.html;
//http://localhost:8080/h2-console;

@EnableCaching
@SpringBootApplication
public class ProjetoDeliveryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoDeliveryApiApplication.class, args);
	}

}
