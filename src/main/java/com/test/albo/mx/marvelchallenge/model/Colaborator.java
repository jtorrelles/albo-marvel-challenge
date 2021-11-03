package com.test.albo.mx.marvelchallenge.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Colaborator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long marvelId;
	private String shortName;
	private String fullName;
	private LocalDateTime lastSync;

}
