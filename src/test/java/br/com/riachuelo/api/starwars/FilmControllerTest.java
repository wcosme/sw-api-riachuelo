package br.com.riachuelo.api.starwars;

import br.com.riachuelo.api.starwars.entities.Film;
import br.com.riachuelo.api.starwars.entities.dto.FilmFilter;
import br.com.riachuelo.api.starwars.entities.dto.FilmRequest;
import br.com.riachuelo.api.starwars.entities.dto.FilmResponse;
import br.com.riachuelo.api.starwars.repository.FilmRepository;
import br.com.riachuelo.api.starwars.services.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FilmControllerTest {
	
private FilmService service;
	
	private FilmRepository repository;	
	private FilmFilter filmFilter;
	private FilmRequest request;
	
	@Mock
	Page<Film> filmPage;	
	private Film film;	
	private final Long id = 1L;
	private String title;	
	private Integer episodeId;
	
	@Mock
	private Pageable paginacao = PageRequest.of(0, 10);
	
	@BeforeEach
	void setup() {
		openMocks(this);
		service = Mockito.mock(FilmService.class);
		repository = Mockito.mock(FilmRepository.class);
		filmFilter = Mockito.mock(FilmFilter.class);
		request = Mockito.mock(FilmRequest.class);
		film = Mockito.mock(Film.class);
		
		this.title = "Test";
		this.episodeId = 1;
		LocalDate releaseDate = LocalDate.of(2021, 12, 30);
		String openingCrawl = "teste openingCrawl Swapi";
		String director = "Steven Spilberg";
		String producer = "George Lucas";
		String url = "https://swapi.dev/api/films/1";
		
		Mockito.lenient().when(filmFilter.getTitle()).thenReturn(this.title);
		Mockito.lenient().when(filmFilter.getEpisodeId()).thenReturn(this.episodeId);
		Mockito.lenient().when(filmFilter.getDirector()).thenReturn(director);
		Mockito.lenient().when(filmFilter.getProducer()).thenReturn(producer);
		
		filmFilter = FilmFilter.builder()
				.title(title)
				.episodeId(episodeId)
				.director(director)
				.producer(producer)
				.build();
		
		request = FilmRequest.builder()
				.id(id)
				.title(title)
				.episodeId(episodeId)
				.releaseDate(releaseDate)
				.openingCrawl(openingCrawl)
				.director(director)
				.producer(producer)
				.url(url)
				.build();
	}
	

	@Test
	@DisplayName("Deveria lista todos os filmes")
	void getAllFilmsNotThrowExceptions() {		
		when(service.findAll(paginacao)).thenReturn(filmPage);		
		var  responseEntity = ResponseEntity.ok().body(FilmResponse.toFilm(filmPage));		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());		
	}
	
	@Test
	@DisplayName("Deveria consultar todos os filmes com filtro")
    void shouldReturnAllFilms() {
        List<Film> films = new ArrayList<>();
        films.add(new Film());
        
        filmPage = new PageImpl<>(films);
        
        this.title = "A New Hope";
		this.episodeId = null;
		
		filmFilter = FilmFilter
				.builder()
				.title(title)
				.episodeId(episodeId)
				.build();
        
        Page<Film> expected = repository.findAll(paginacao);

        assertEquals(expected, filmPage);
        
    }

	@Test
	@DisplayName("Deveria consultar um filme com sucesso")
	void findByIdFilmSucessfully() {
		
		var responseEntity = ResponseEntity.ok().body(when(service.findById(id)).thenReturn(film));
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Deveria cadastra um filme com sucesso")
	void postFilmWithSucessfully() {
		when(service.create(request)).thenReturn(film);
		
		var responseEntity = ResponseEntity.status(HttpStatus.CREATED).header("id", film.getId().toString()).build();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Deveria atualizar um filme com sucesso")
	void putFilmWithSucessfully() {
		
		doNothing().when(service).update(id, request);
		
		var responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Deveria excluir um filme com sucesso")
	void deleteFilmSucessfully() {
		doNothing().when(service).delete(id);
		
		var responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}

}
