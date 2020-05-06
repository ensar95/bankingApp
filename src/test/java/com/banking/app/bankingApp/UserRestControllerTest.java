package com.banking.app.bankingApp;

import com.banking.app.bankingApp.controller.UserController;
import com.banking.app.bankingApp.request.users.CreateUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenUsersExists_thenReturns200() throws Exception{
        mockMvc.perform(get("/users")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
    @Test
    void whenUserCreated_thenReturns201() throws Exception{
        CreateUser createUser=new CreateUser();
        createUser.setFirstName("Ensar");
        createUser.setLastName("Skopljak");
        createUser.setCurrentAddress("adresa");
        createUser.setDateOfBirth(new Date(1995-02-19));
        createUser.setEmail("ensar@nesto");
        createUser.setOccupation("unemployed");
        createUser.setPassword("pas123");
        createUser.setPhoneNumber("061356734");

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(createUser))
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }
}
