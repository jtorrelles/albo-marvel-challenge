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

import com.test.albo.mx.marvelchallenge.controller.ColaboratorsController;
import com.test.albo.mx.marvelchallenge.dto.ColaboratorsDto;
import com.test.albo.mx.marvelchallenge.exception.ColaboratorsNoContentException;
import com.test.albo.mx.marvelchallenge.service.ColaboratorsService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ColaboratorsController.class)
public class ColaboratorsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ColaboratorsService colaboratorService;

	@Test
	public void getColaborators_ShouldBeOK() throws Exception {

		when(colaboratorService.getColaborators(anyString())).thenReturn(new ColaboratorsDto(LocalDateTime.now(),
				new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>()));

		mockMvc.perform(get("/colaborators/{name}", "capamerica")).andExpect(status().isOk())
				.andExpect(jsonPath("$.last_sync").isNotEmpty()).andExpect(jsonPath("$.editors").isArray())
				.andExpect(jsonPath("$.writers").isArray()).andExpect(jsonPath("$.colorists").isArray());
	}

	@Test
	public void getColaborators_ShouldBeNoContent() throws Exception {

		when(colaboratorService.getColaborators(anyString())).thenThrow(new ColaboratorsNoContentException());

		mockMvc.perform(get("/colaborators/{name}", "capamerica")).andExpect(status().isNoContent());
	}

}
