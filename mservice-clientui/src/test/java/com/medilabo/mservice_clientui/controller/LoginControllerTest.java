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
class LoginControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	void shwoTheLoginPageAndReturnOk() throws Exception {

		//testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui/login"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("request"))
			.andExpect(MockMvcResultMatchers.view().name("login"));
		
	}

}
