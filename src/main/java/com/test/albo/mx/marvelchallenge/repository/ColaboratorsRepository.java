package com.test.albo.mx.marvelchallenge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.albo.mx.marvelchallenge.model.Colaborators;

@Repository
public interface ColaboratorsRepository extends CrudRepository<Colaborators, Long> {
	// buscar un colaborador por nombre
	Optional<Colaborators> findByName(String shortName);

	// buscar un colaborador por nombre y rol
	Optional<Colaborators> findByNameAndRole(String shortName, String role);
}
