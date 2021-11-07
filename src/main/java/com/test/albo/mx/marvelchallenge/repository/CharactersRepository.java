package com.test.albo.mx.marvelchallenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.test.albo.mx.marvelchallenge.model.Characters;

public interface CharactersRepository extends CrudRepository<Characters, Long> {

	// buscar un personaje por el nombre corto y que tenga habilitada la
	// sincronizacion. Ejemplo (capamerica)
	Optional<Characters> findByShortNameAndSync(String shortName, boolean sync);

	// buscar un personaje por el nombre completo. Ejemplo (Captain America)
	Optional<Characters> findByFullName(String fullName);

	// buscar un personajes con sincronizacion activa
	List<Characters> findBySync(boolean isSync);
}
