package com.medilabo.mservice_clientui.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void getAccesToTheHomePage() throws Exception {
		
		//Testinf request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("home"));
		
	}

}
