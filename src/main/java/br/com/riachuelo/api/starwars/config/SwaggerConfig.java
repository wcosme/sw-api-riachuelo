package br.com.riachuelo.api.starwars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("Documentação da SWAPI")
	            .description("Documentação da API.")	            
	            .license("Apache Licence Version 2.0")
                .licenseUrl("https://apache.org")
	            .version("1.0.0")
	            .contact(new Contact("Wallace Gonçalves", "", "wallacecosme@gmail.com"))
	            .build();
	}
	
}
