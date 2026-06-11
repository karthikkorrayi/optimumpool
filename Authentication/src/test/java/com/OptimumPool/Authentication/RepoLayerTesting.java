package com.OptimumPool.Authentication;

import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepoLayerTesting {

    @Autowired
    private UserRepo repo;
    private User user;

    @BeforeEach
    void setup(){
        user = new User("username", "pass@1234", 1234567890);
    }
    @AfterEach
    void tearDown(){
        user = null;
        repo.deleteAll();
    }

    @Test
    @DisplayName("Test case for saving User object")
    public void registrationTest(){
        repo.save(user);
        User u1 = repo.findById(user.getUsername()).get();
        assertNotNull(u1);
        assertEquals(u1.getUsername(),user.getUsername());
    }

    @Test
    @DisplayName("Test case to get profile")
    void retrieveProfile(){
        repo.save(user);
        User u1 = repo.findByUsername(user.getUsername());
        assertEquals(u1.getUsername(), user.getUsername());
    }

    @Test
    @DisplayName("test case to check delete function")
    public void deleteTest(){
        repo.save(user);
        User u1 = repo.findById(user.getUsername()).get();
        repo.delete(u1);
        assertEquals(Optional.empty(),repo.findById(user.getUsername()));
    }

}
