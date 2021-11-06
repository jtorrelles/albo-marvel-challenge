package com.test.albo.mx.marvelchallenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.albo.mx.marvelchallenge.model.Comics;

@Repository
public interface ComicsRepository extends JpaRepository<Comics, Long>, CrudRepository<Comics, Long> {
	Optional<Comics> findByName(String comicName);
}
