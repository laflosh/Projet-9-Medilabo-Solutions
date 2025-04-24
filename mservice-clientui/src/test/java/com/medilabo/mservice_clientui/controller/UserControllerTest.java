package com.medilabo.mservice_clientui.controller;

import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

import com.medilabo.mservice_clientui.beans.UserBean;
import com.medilabo.mservice_clientui.proxys.MServiceUserProxy;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Mock
	MServiceUserProxy userProxy;
	
	@InjectMocks
	UserController userController;
	
    @BeforeEach
    void setup() {
    	
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        
    }
	
	@Test
	public void showTheListOfUsersAndReturnOk() throws Exception {
		
		//Mock users
		List<UserBean> mockUsers = Arrays.asList(
                new UserBean(1,"JohnDoc", "Doe", "John","Doe@hotmail.fr", "123456", "01-01-1990", new Date(), "ADMIN"),
                new UserBean(2,"SmithDoc", "Smith", "Jane", "Smith@hotmail.fr", "654321", "10-05-1985", new Date(), "USER")
        );
		
		//Mock return
		Mockito.when(userProxy.getAllUsers()).thenReturn(mockUsers);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui/users"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("users"))
			.andExpect(MockMvcResultMatchers.view().name("users/list"));
		
	}
	
	@Test
	public void getAccessToTheAddUserPageAndReturnOk() throws Exception {
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui/users/add"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.view().name("users/add"));
		
	}
	
	@Test
	public void postNewValidateUserAndReturnTheResumePage() throws Exception {
		
		//Mock user
		UserBean mockUser = new UserBean(1,"JohnDoc", "Doe", "John","Doe@hotmail.fr", "123456", "01-01-1990", new Date(), "ADMIN");
		
		//Mock return
		Mockito.when(userProxy.addNewUser(any(UserBean.class))).thenReturn(mockUser);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/ui/users/validate")
				.param("name", "Doe")
				.param("firstName", "John")
				.param("username", "JohnDoc")
				.param("mail", "Doe@hotmail.fr")
				.param("password", "123456")
				.param("birthDate", "01-01-1990")
				.param("role", "ADMIN"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.view().name("resume"));
		
	}
	
	@Test
	public void postNoneValidateNewUserAndReturnAddPage() throws Exception {
		
		//Testing request 
		mockMvc.perform(MockMvcRequestBuilders.post("/ui/users/validate"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("user", "name", "firstName", "username", "mail", "password", "role"))
			.andExpect(MockMvcResultMatchers.view().name("users/add"));
		
	}
	
	@Test
	public void getAccessToUpdatePageAndReturnOk() throws Exception {
		
		//Mock user
		UserBean mockUser = new UserBean(1,"JohnDoc", "Doe", "John","Doe@hotmail.fr", "123456", "01-01-1990", new Date(), "ADMIN");
		
		//Mock return
		Mockito.when(userProxy.getOneUserById(mockUser.getId())).thenReturn(mockUser);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui/users/update/" + mockUser.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.view().name("users/update"));
		
	}
	
	@Test
	public void postValidateUpdateUserAndReturnResumePage() throws Exception {
		
		//Mock user
		UserBean mockUser = new UserBean(1,"JohnDoc", "Doe", "John","Doe@hotmail.fr", "123456", "01-01-1990", new Date(), "ADMIN");
		
		//Mock return
		Mockito.when(userProxy.getOneUserById(mockUser.getId())).thenReturn(mockUser);
		Mockito.when(userProxy.updateExistingUser(any(UserBean.class))).thenReturn(mockUser);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/ui/users/update/" + mockUser.getId())
				.param("name", "DoeJohn")
				.param("firstName", "JohnDoe")
				.param("username", "DoeDoc")
				.param("mail", "John@hotmail.fr")
				.param("password", "654321")
				.param("birthDate", "01-01-1995")
				.param("role", "USER"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.view().name("resume"));
		
	}
	
	@Test
	public void postNoneValidateUpdateUserAndReturnUpdatePage() throws Exception {
		
		//Mock user
		UserBean mockUser = new UserBean(1,"JohnDoc", "Doe", "John","Doe@hotmail.fr", "123456", "01-01-1990", new Date(), "ADMIN");
		
		//Mock return
		Mockito.when(userProxy.getOneUserById(mockUser.getId())).thenReturn(mockUser);
		
		//Testing request 
		mockMvc.perform(MockMvcRequestBuilders.post("/ui/users/update/" + mockUser.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("user", "name", "firstName", "username", "mail", "password", "role"))
			.andExpect(MockMvcResultMatchers.view().name("users/update"));
		
	}
	
	@Test
	public void getAccessToConfirmationPageBeforeDeleteUserAndReturnOk() throws Exception {
		
		//Mock user
		UserBean mockUser = new UserBean(1,"JohnDoc", "Doe", "John","Doe@hotmail.fr", "123456", "01-01-1990", new Date(), "ADMIN");
		
		//Mock return
		Mockito.when(userProxy.addNewUser(any(UserBean.class))).thenReturn(mockUser);
		Mockito.when(userProxy.getOneUserById(mockUser.getId())).thenReturn(mockUser);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui/users/confirmation/" + mockUser.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.view().name("confirmation"));
		
	}
	
	@Test
	public void deleteAnExistingUserInTheDatabaseAndReturnToTheUserListPage() throws Exception {
		
		//Mock user
		UserBean mockUser = new UserBean(1,"JohnDoc", "Doe", "John","Doe@hotmail.fr", "123456", "01-01-1990", new Date(), "ADMIN");
		
		//Mock return
		Mockito.when(userProxy.getOneUserById(mockUser.getId())).thenReturn(mockUser);
		
		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ui/users/delete/" + mockUser.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("users"))
			.andExpect(MockMvcResultMatchers.view().name("users/list"));
		
	}

}
