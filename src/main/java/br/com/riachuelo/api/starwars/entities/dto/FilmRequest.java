package br.com.riachuelo.api.starwars.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import br.com.riachuelo.api.starwars.entities.Film;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class FilmRequest {
	
	private Long id;
	
	@NotBlank
	private String title;	
	private Integer episodeId;	
	private String openingCrawl;	
	private String director;	
	private String producer;
	private LocalDate releaseDate;
	private String url;
	
	public Film requestFilmBuilder() {
		return Film.builder()
				.title(this.title)
				.episodeId(episodeId)
				.openingCrawl(openingCrawl)
				.director(director)
				.producer(producer)
				.releaseDate(releaseDate)
				.url(url)
				.build();
	}

}
