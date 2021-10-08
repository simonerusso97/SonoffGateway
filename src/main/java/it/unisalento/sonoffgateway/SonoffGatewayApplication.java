package it.unisalento.sonoffgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SonoffGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SonoffGatewayApplication.class, args);
		
		//TODO: probabilmente una connessione mqtt va qui
	}

}
