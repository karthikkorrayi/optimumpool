package com.OptimumPool.Authentication;

import com.OptimumPool.Authentication.Controller.UserController;
import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerLayerTesting {
    UserService ser = mock(UserService.class);
    @InjectMocks
    private UserController con;
    @Autowired
    private MockMvc mockmvc;
    private User user;
    @BeforeEach
    void setup()
    {
        user=new User("username", "pass@1234", 1234567890);
        mockmvc= MockMvcBuilders.standaloneSetup(con).build();
    }
    @AfterEach
    void tearDown()
    {
        user=null;
    }
    @Test
    @DisplayName("Test save user in controll")
    void givenUserSavedReturnSaveUser() throws Exception
    {
        when(ser.addUser(user)).thenReturn(user);
        mockmvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isCreated());
    }
    public String jsonToString(User u) throws JsonProcessingException
    {
        ObjectMapper obj=new ObjectMapper();
        String jsoncontent=obj.writeValueAsString(u);
        return jsoncontent;
    }

    @Test
    @DisplayName("Test get user by id")
    void shouldFetchUserById() throws Exception {
        when(ser.getProfile()).thenReturn(user);
        this.mockmvc.perform(get("/login/profile"))
                .andExpect(status().isOk());
    }

}
