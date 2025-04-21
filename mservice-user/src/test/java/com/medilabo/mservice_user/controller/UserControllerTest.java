package com.medilabo.mservice_user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.mservice_user.model.User;
import com.medilabo.mservice_user.repository.UserRepository;
import com.medilabo.mservice_user.service.UserService;

import jakarta.ws.rs.core.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	final String TEST_USER_PREFIX = "test_user_";
	List<User> testUsers = new ArrayList<>();

	@Autowired
	UserRepository userRepository;
	
	@Mock
	UserService userService;
	
	@InjectMocks
	UserController userController;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	public void setUp(){
		
		//Creating a user test
		User testUser = new User();
		testUser.setUsername(TEST_USER_PREFIX + "1");
		testUser.setName("John");
		testUser.setFirstName("Doe");
		testUser.setMail("test@test.fr");
		testUser.setPassword("123456");
		testUser.setBirthDate("00-00-0000");
		testUser.setRole("ADMIN");

		//Adding to the database
		userRepository.save(testUser);
		testUsers.add(testUser);

	}

	@AfterEach
	public void tearDown() {

		//Delete all the test entity in the database
		testUsers.forEach(testUser -> {
			if(userRepository.existsById(testUser.getId())) {
				userRepository.deleteById(testUser.getId());
			}
		});
		testUsers.clear();

	}
	
	@Test
	public void getAllUserInDatabseAndReturnOk() throws Exception {
		
		//Testing request 
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void getOneUserInDatabseAndReturnOk() throws Exception {
		
		//Get testing user
		User testUser = testUsers.get(0);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/" + testUser.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
		
	}
	
	@Test
	public void addNewUserInDatabaseAndReturnCreated() throws Exception {
		
		//Create new test user
		User testUser = new User();
		testUser.setUsername(TEST_USER_PREFIX + "2");
		testUser.setName("Michel");
		testUser.setFirstName("medecin");
		testUser.setMail("test@test.fr");
		testUser.setPassword("123456");
		testUser.setBirthDate("00-00-0000");
		testUser.setRole("ADMIN");
		
		String userAsString = objectMapper.writeValueAsString(testUser);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
			.contentType(MediaType.APPLICATION_JSON).content(userAsString))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
		List<User> usersDatabase = StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
		
		testUsers.add(usersDatabase.get(usersDatabase.size() - 1));
		
	}
	
	@Test
	public void AddNewUserWithWrongArgumentAndReturnBadRequest() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		
		//Create new test user
		User testUser = new User();
		testUser.setUsername(TEST_USER_PREFIX + "2");
		testUser.setName("Michel");
		testUser.setFirstName("medecin");
		testUser.setMail("test@test.fr");
		testUser.setPassword("123456");
		testUser.setBirthDate("00-00-0000");
		testUser.setRole("ADMIN");
		
		String userAsString = objectMapper.writeValueAsString(testUser);
		
		//Mock return 
		Mockito.when(userService.addNewUser(Mockito.any())).thenThrow(new RuntimeException());
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
			.contentType(MediaType.APPLICATION_JSON).content(userAsString))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void updateAnExistingUserInTheDatabaseAndReturnCreated() throws Exception {
		
		//Fetching the existing user
		User testUser = testUsers.get(0);
		testUser.setUsername(TEST_USER_PREFIX + "update");
		testUser.setName("Nom");
		testUser.setFirstName("Prenom");
		testUser.setMail("test@try.fr");
		testUser.setPassword("654321");
		testUser.setBirthDate("11-00-0000");
		testUser.setRole("USER");
		
		String userAsString = objectMapper.writeValueAsString(testUser);
		
		//testing request
		mockMvc.perform(MockMvcRequestBuilders.put("/api/users")
				.contentType(MediaType.APPLICATION_JSON).content(userAsString))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void updateAnExistingUserInTheDatabaseWithWrongArgumentAndReturnBadRequest() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		
		//Fetching the existing user
		User testUser = testUsers.get(0);
		testUser.setUsername(TEST_USER_PREFIX + "update");
		testUser.setName("Nom");
		testUser.setFirstName("Prenom");
		testUser.setMail("test@try.fr");
		testUser.setPassword("654321");
		testUser.setBirthDate("11-00-0000");
		testUser.setRole("USER");
		
		String userAsString = objectMapper.writeValueAsString(testUser);
		
		//Mock return 
		Mockito.when(userService.updateExistingUser(Mockito.any())).thenThrow(new RuntimeException());
		
		//testing request
		mockMvc.perform(MockMvcRequestBuilders.put("/api/users")
				.contentType(MediaType.APPLICATION_JSON).content(userAsString))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void deleteAnExistingInTheDatabaseAndReturnNoContent() throws Exception {
		
		//Fetching user to delete 
		User testUser = userRepository.findById(testUsers.get(0).getId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/" + testUser.getId()))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}
	
	@Test
	public void deleteANoneExistingInTheDatabaseAndReturnNoContent() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/" + -1))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	@Test
	public void getOneUserInTheDatabseWithUsernameAndReturnOk() throws Exception {
		
		//Get testing user
		User testUser = testUsers.get(0);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/username/" + testUser.getUsername()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void getOneUserInTheDatabseWithMailAndReturnOk() throws Exception {
		
		//Get testing user
		User testUser = testUsers.get(0);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/mail/" + testUser.getMail()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.not(Matchers.empty())));
		
	}

}
