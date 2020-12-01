package br.com.solangedomingues.transferapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(
		info = @Info(
				title = "TRANSFER-API",
				description = "API that allows the registration of new accounts and the transfer between them.",
				version = "1.0.0",
				contact = @Contact(name = "Solange Domingues", url = "", email = "contato@solangedomingues.com.br" )
		)
)
@SpringBootApplication
public class TransferApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferApiApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
