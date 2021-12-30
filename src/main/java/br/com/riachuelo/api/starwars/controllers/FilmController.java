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
import br.com.riachuelo.api.starwars.entities.dto.FilmRequest;
import br.com.riachuelo.api.starwars.entities.dto.FilmResponse;
import br.com.riachuelo.api.starwars.services.FilmService;
import io.swagger.annotations.ApiOperation;
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
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid FilmRequest request) {
		service.update(id, request);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<FilmResponse> findById(@PathVariable("id") Long id) {
		var response = new FilmResponse(service.findById(id));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição com sucesso", response = FilmResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	@ApiOperation(value = "Find all films")
	@GetMapping
	public ResponseEntity<Page<FilmResponse>> findAll(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "episodeId", required = false) Integer episodeId,
			@RequestParam(value = "director", required = false) String director,
			@RequestParam(value = "producer", required = false) String producer,
			@PageableDefault(page = 0, size = 10) Pageable paginacao) {

		Page<Film> film = service.findAll(paginacao);

		return ResponseEntity.ok().body(FilmResponse.toFilm(film));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
