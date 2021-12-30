package br.com.riachuelo.api.starwars.services;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.riachuelo.api.starwars.entities.Film;
import br.com.riachuelo.api.starwars.entities.dto.FilmFilter;
import br.com.riachuelo.api.starwars.entities.dto.FilmRequest;

public interface FilmService {

	Film create(FilmRequest filmRequest);
	Film findById(Long id);
	Page<Film> findAll(FilmFilter filter, Pageable paginacao);
	void update(Long id, @Valid FilmRequest request);
	void delete(Long id);
}
