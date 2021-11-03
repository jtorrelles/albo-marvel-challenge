package com.test.albo.mx.marvelchallenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.albo.mx.marvelchallenge.model.Colaborator;

@Repository
public interface ColaboratorsRepository extends CrudRepository<Colaborator, Long> {
	Colaborator findByShortName(String shortName);
}
