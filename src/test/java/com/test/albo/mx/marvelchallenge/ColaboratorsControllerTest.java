package com.test.albo.mx.marvelchallenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.test.albo.mx.marvelchallenge.controller.ColaboratorsController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ColaboratorsController.class)
public class ColaboratorsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getColaborators_ShouldBeOK() throws Exception {
		mockMvc.perform(get("/colaborators/{name}", "capamerica")).andExpect(status().isOk())
				.andExpect(jsonPath("$.last_sync").isNotEmpty()).andExpect(jsonPath("$.editors").isArray())
				.andExpect(jsonPath("$.writers").isArray()).andExpect(jsonPath("$.colorists").isArray());
	}

}
