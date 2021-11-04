package com.test.albo.mx.marvelchallenge;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.test.albo.mx.marvelchallenge.controller.CharactersController;
import com.test.albo.mx.marvelchallenge.dto.CharactersDto;
import com.test.albo.mx.marvelchallenge.dto.PartnersDto;
import com.test.albo.mx.marvelchallenge.exception.GenericNotFoundException;
import com.test.albo.mx.marvelchallenge.service.CharactersService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CharactersController.class)
public class CharactersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CharactersService charactersService;

	@Test
	public void getCharacters_ShouldBeOK() throws Exception {

		when(charactersService.getCharacters(anyString()))
				.thenReturn(new CharactersDto(LocalDateTime.now(), new ArrayList<PartnersDto>() {
					private static final long serialVersionUID = 1L;
					{
						add(new PartnersDto("Character1", new ArrayList<String>()));
					}
				}));

		mockMvc.perform(get("/characters/{name}", "capamerica")).andExpect(status().isOk())
				.andExpect(jsonPath("$.last_sync").isNotEmpty()).andExpect(jsonPath("$.characters").exists())
				.andExpect(jsonPath("$.characters").isArray()).andExpect(jsonPath("$.characters[*].character").exists())
				.andExpect(jsonPath("$..characters[*].comics").isArray());
	}

	@Test
	public void getCharacters_ShouldBeNotFound() throws Exception {

		when(charactersService.getCharacters(anyString())).thenThrow(new GenericNotFoundException());

		mockMvc.perform(get("/characters/{name}", "capamerica")).andExpect(status().isNotFound());
	}

}
