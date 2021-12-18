package br.com.riachuelo.api.starwars.client;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.riachuelo.api.starwars.entities.dto.FilmResponse;
import br.com.riachuelo.api.starwars.services.exceptions.ServiceUnavailable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class RestTeamplate {
	
	final static String url = "https://swapi.co/api/films/";
	   
    RestTemplate restTemplate = new RestTemplate();

	public ResponseEntity<FilmResponse> getFilms() {
		try { 
			return restTemplate.exchange(url, HttpMethod.GET,geraHeader(), FilmResponse.class);
   		}catch(Exception e) {
   			throw new ServiceUnavailable("Serviço fora do ar");
   		}
   	}
	
	public HttpEntity<String> geraHeader(){
		
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.106 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		return entity;
	}

}
