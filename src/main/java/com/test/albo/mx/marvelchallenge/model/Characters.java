package com.test.albo.mx.marvelchallenge.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Characters {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "short_name")
	private String shortName;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "marvel_id")
	private long marvelId;

	private boolean sync;
	@Column(name = "last_sync")
	private LocalDateTime lastSync;

	@OneToMany
	@JoinTable(name = "characters_comics", joinColumns = @JoinColumn(name = "character_id"), inverseJoinColumns = @JoinColumn(name = "comic_id"))
	private List<Comics> comics;
}
