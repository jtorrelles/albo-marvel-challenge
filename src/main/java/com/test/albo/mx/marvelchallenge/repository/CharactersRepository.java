package com.test.albo.mx.marvelchallenge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.test.albo.mx.marvelchallenge.model.Characters;

public interface CharactersRepository extends CrudRepository<Characters, Long> {
	Optional<Characters> findByShortName(String shortName);
}
