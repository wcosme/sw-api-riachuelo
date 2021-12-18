package br.com.riachuelo.api.starwars.entities.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.riachuelo.api.starwars.entities.Film;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FilmResponse {

	private String title;
	private Integer episodeId;
	private String openingCrawl;
	private String director;
	private String producer;
	private LocalDate replaceDate;
	private String url;
	
	public FilmResponse() {
	}

	public FilmResponse(Film film) {
		this.title = film.getTitle();
		this.episodeId = film.getEpisodeId();
		this.openingCrawl = film.getOpeningCrawl();
		this.director = film.getDirector();
		this.producer = film.getProducer();
		this.replaceDate = film.getReleaseDate();
		this.url = film.getUrl();
	}
	
	public static List<FilmResponse> toFilm(List<Film> films) {
		return films.stream().map(FilmResponse::new).collect(Collectors.toList());
	}

	public static Page<FilmResponse> toFilm(Page<Film> films) {
		return films.map(FilmResponse::new);
	}
}
