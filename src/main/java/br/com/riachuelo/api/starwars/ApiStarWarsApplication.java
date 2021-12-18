package br.com.riachuelo.api.starwars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import br.com.riachuelo.api.starwars.entities.Film;
import br.com.riachuelo.api.starwars.repository.FilmRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableSpringDataWebSupport
@EnableCaching
@SpringBootApplication
public class ApiStarWarsApplication implements CommandLineRunner {
	
	@Autowired
	private FilmRepository filmRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiStarWarsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Film film;
		log.info("Buscando todos os filmes...");
		log.info("-------------------------------");
		if(filmRepository.count() == 0) {
			film = filmRepository.save(new Film());
			filmRepository.deleteById(film.getId());
		}
	}
}
