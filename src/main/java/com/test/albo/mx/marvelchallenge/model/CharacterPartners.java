package com.test.albo.mx.marvelchallenge.model;

import java.util.List;

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
public class CharacterPartners {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	@JoinTable(name = "characters_comics_character_partners", joinColumns = @JoinColumn(name = "character_partner_id"), inverseJoinColumns = @JoinColumn(name = "comic_id"))
	@OneToMany
	private List<Comics> comics;
}
