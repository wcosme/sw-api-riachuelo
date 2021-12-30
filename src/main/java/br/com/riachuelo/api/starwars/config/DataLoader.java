package br.com.riachuelo.api.starwars.config;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import br.com.riachuelo.api.starwars.entities.Film;
import br.com.riachuelo.api.starwars.entities.dto.FilmRequest;
import br.com.riachuelo.api.starwars.repository.FilmRepository;

@Configuration
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private FilmRepository filmRepository;

	public DataLoader(FilmRepository filmRepository) {
		this.filmRepository = filmRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

		filmRepository.deleteAll();
		int i = 1;
		while (i <= 10) {
			var request = FilmRequest.builder().title("A New Hope " + i).episodeId(i).releaseDate(LocalDate.now())
					.director("George Lucas").producer("Gary Kurtz, Rick McCallum")
					.openingCrawl("It is a period of civil war.\n\nRebel spaceships, striking\n\nfrom a hidden base, "
							+ "have won\n\ntheir first victory against\n\nthe evil Galactic Empire.\n\n\n\nDuring the battle, "
							+ "Rebel\n\nspies managed to steal secret\r\nplans to the Empire's\n\nultimate weapon, the DEATH\n\nSTAR, "
							+ "an armored space\n\nstation with enough power\n\nto destroy an entire planet.\n\n\n\nPursued by the Empire's\n\nsinister agents, "
							+ "Princess\n\nLeia races home aboard her\n\nstarship, custodian of the\n\nstolen "
							+ "plans that can save her\n\npeople and restore\n\nfreedom to the galaxy....")
					.url("https://swapi.dev/api/films").build();

			createFilmIfNotFound(request);

			i++;
		}
	}

	private Film createFilmIfNotFound(final FilmRequest request) {
		Optional<Film> obj = filmRepository.findByTitleIgnoreCase(request.getTitle());
		if (obj.isPresent()) {
			return obj.get();
		}

		request.setUrl(request.getUrl() + "/" + request.getEpisodeId());

		return filmRepository.save(request.requestFilmBuilder());
	}
}
