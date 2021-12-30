package br.com.riachuelo.api.starwars.services;

import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.riachuelo.api.starwars.entities.Film;
import br.com.riachuelo.api.starwars.entities.dto.FilmRequest;
import br.com.riachuelo.api.starwars.repository.FilmRepository;
import br.com.riachuelo.api.starwars.services.exceptions.ResourceNotFoundException;

@Service
public class FilmServiceImpl implements FilmService {

	@Autowired
	private FilmRepository repository;

	@Transactional
	@Override
	public Film create(FilmRequest filmRequest) {
		return repository.save(filmRequest.requestFilmBuilder());
	}

	@Transactional(readOnly = true)
	@Override
	public Film findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Film not found"));
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = "registerFilms", allEntries = true)
	public void update(Long id, @Valid FilmRequest request) {
		var filmExists = findById(id);

		if (!request.getTitle().equals(filmExists.getTitle())) {
			request.setId(null);
			request.setEpisodeId(request.getEpisodeId() + 1);
			create(request);
		} else {
			BeanUtils.copyProperties(request, filmExists, "id");
			repository.save(filmExists);
		}
	}

	@Transactional
	@Override
	public void delete(Long id) {
		Optional<Film> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Film> findAll(Pageable paginacao) {
		Page<Film> film = this.repository.findAll(paginacao);

		if (film.isEmpty()) {
			throw new ResourceNotFoundException("Film not found!");
		}

		return film;
	}
}
