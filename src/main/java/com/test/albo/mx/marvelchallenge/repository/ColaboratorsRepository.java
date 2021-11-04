package com.test.albo.mx.marvelchallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.albo.mx.marvelchallenge.model.Colaborators;

@Repository
public interface ColaboratorsRepository extends CrudRepository<Colaborators, Long> {
	Colaborators findByName(String shortName);
}
