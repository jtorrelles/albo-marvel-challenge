package com.test.albo.mx.marvelchallenge.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Characters {

	private long id;
	private String shortName;
	private String fullName;
	private long marvelId;
	private boolean sync;
	private LocalDateTime lastSync;

	private Set<Comics> comics = new HashSet<Comics>(0);

	public Characters() {
	}

	public Characters(String shortName, String fullName, long marvelId, boolean sync, LocalDateTime lastSync) {
		this.shortName = shortName;
		this.fullName = fullName;
		this.marvelId = marvelId;
		this.sync = sync;
		this.lastSync = lastSync;
	}

	public Characters(long id, String shortName, String fullName, long marvelId, boolean sync, LocalDateTime lastSync) {
		this(shortName, fullName, marvelId, sync, lastSync);
		this.id = id;
	}

	@Column(name = "short_name")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "full_name")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "marvel_id")
	public long getMarvelId() {
		return marvelId;
	}

	public void setMarvelId(long marvelId) {
		this.marvelId = marvelId;
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

	@Column(name = "last_sync")
	public LocalDateTime getLastSync() {
		return lastSync;
	}

	public void setLastSync(LocalDateTime lastSync) {
		this.lastSync = lastSync;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "characters", cascade = { CascadeType.PERSIST })
	public Set<Comics> getComics() {
		return new HashSet<Comics>(comics);
	}

	public void setComics(Set<Comics> comics) {
		this.comics = comics;
	}

	public void addComic(Comics comic) {

		if (!comics.contains(comic)) {
			comics.add(comic);

			comic.addCharacter(this);
		}
	}

	public void removeComic(Comics comic) {

		if (comics.contains(comic)) {
			comics.remove(comic);

			comic.removeCharacter(this);
		}
	}

}
