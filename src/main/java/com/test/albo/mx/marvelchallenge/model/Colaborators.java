package com.test.albo.mx.marvelchallenge.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Colaborators {
	private long id;

	private String name;
	private String role;

	private Set<Comics> comics = new HashSet<Comics>(0);

	public Colaborators() {

	}

	public Colaborators(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public Colaborators(long id, String name, String role) {
		this(name, role);
		this.id = id;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "colaborators", cascade = { CascadeType.PERSIST })
	public Set<Comics> getComics() {
		return comics;
	}

	public void setComics(Set<Comics> comics) {
		this.comics = comics;
	}

	public void addComic(Comics comic) {

		if (!comics.contains(comic)) {
			comics.add(comic);

			comic.addColaborator(this);
		}
	}

	public void removeComic(Comics comic) {

		if (comics.contains(comic)) {
			comics.remove(comic);

			comic.removeColaborator(this);
		}
	}

}
