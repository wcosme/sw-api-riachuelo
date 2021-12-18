package br.com.riachuelo.api.starwars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.riachuelo.api.starwars.entities.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

	Optional<Film> findByTitleIgnoreCase(String title);
}
