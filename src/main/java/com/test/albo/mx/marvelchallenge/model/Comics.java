package com.test.albo.mx.marvelchallenge.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Comics {

	private long id;
	private String name;

	public Comics() {
	}

	public Comics(String name) {
		this.name = name;
	}

	public Comics(long id, String name) {
		this(name);
		this.id = id;
	}

	private Set<Colaborators> colaborators = new HashSet<Colaborators>(0);
	private Set<Characters> characters = new HashSet<Characters>(0);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "characters_comics", joinColumns = @JoinColumn(name = "comic_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	public Set<Characters> getCharacters() {
		return characters;
	}

	public void setCharacters(Set<Characters> characters) {
		this.characters = characters;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "comics_colaborators", joinColumns = @JoinColumn(name = "comic_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "colaborator_id", nullable = false, updatable = false))
	public Set<Colaborators> getColaborators() {
		return colaborators;
	}

	public void setColaborators(Set<Colaborators> colaborators) {
		this.colaborators = colaborators;
	}

	public void addCharacter(Characters character) {
		if (!characters.contains(character)) {
			characters.add(character);

			character.addComic(this);
		}
	}

	public void removeCharacter(Characters character) {
		if (characters.contains(character)) {
			characters.remove(character);

			character.removeComic(this);
		}
	}

	public void addColaborator(Colaborators colaborator) {
		if (!colaborators.contains(colaborator)) {
			colaborators.add(colaborator);

			colaborator.addComic(this);
		}
	}

	public void removeColaborator(Colaborators colaborator) {
		if (colaborators.contains(colaborator)) {
			colaborators.remove(colaborator);

			colaborator.removeComic(this);
		}
	}

}
