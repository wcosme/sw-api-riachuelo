package br.com.riachuelo.api.starwars.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.riachuelo.api.starwars.entities.Film;
import br.com.riachuelo.api.starwars.entities.dto.FilmFilter;
import br.com.riachuelo.api.starwars.entities.dto.FilmRequest;
import br.com.riachuelo.api.starwars.entities.dto.FilmResponse;
import br.com.riachuelo.api.starwars.services.FilmService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/films")
public class FilmController {

	@Autowired
	private FilmService service;

	@PostMapping
	public ResponseEntity<Film> create(@RequestBody FilmRequest filmRequest) {
		var film = service.create(filmRequest);
		return ResponseEntity.status(HttpStatus.CREATED).header("id", film.getId().toString()).build();

	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid FilmRequest requestPut) {
		service.update(id, requestPut);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<FilmResponse> findById(@PathVariable("id") Long id) {
		var response = new FilmResponse(service.findById(id));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Requisição com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilmResponse.class))),
			@ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(title = "Resource not found"))),
			@ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(title = "Missing or invalid request body"))),
			@ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(title = "Internal Error"))), })
	@GetMapping
	public ResponseEntity<Page<FilmResponse>> findAll(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "episode_id", required = false) Integer episodeId,
			@RequestParam(value = "director", required = false) String director,
			@RequestParam(value = "producer", required = false) String producer,
			@PageableDefault(page = 0, size = 10) Pageable paginacao) {

		var filmFilter = FilmFilter.builder().title(title).episodeId(episodeId).director(director).producer(producer).build();

		Page<Film> film = service.findAll(filmFilter, paginacao);

		return ResponseEntity.ok().body(FilmResponse.toFilm(film));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
