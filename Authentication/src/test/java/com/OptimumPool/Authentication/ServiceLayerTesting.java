package com.OptimumPool.Authentication;

import com.OptimumPool.Authentication.Exception.UserAlreadyExist;
import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Repository.UserRepo;
import com.OptimumPool.Authentication.Service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServiceLayerTesting {

    UserRepo repo = mock(UserRepo.class);
    @InjectMocks
    private UserService ser;
    User user;
    @BeforeEach
    public void SetUp(){
        user = new User("username", "pass@123", 1234567890);
    }

    @AfterEach
    public void tearDown(){
        user = null;
    }

    @Test
    @DisplayName("Check Save Success")
    public void givenUserToSaveReturnSaveUserSuccess() throws UserAlreadyExist {
        when(repo.findById(user.getUsername())).thenReturn(Optional.ofNullable(null));
        when(repo.save(any())).thenReturn(user);
        assertEquals(ser.addUser(user).getUsername(),user.getUsername());
    }

    @Test
    @DisplayName("Check Delete Success")
    public void deleteTest(){
        when(repo.findById(user.getUsername())).thenReturn(Optional.ofNullable(null));
        boolean status = ser.getDelete();
        assertEquals(status, true);
        verify(repo,times(1)).deleteById(any());
    }
}
