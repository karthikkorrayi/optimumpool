package com.OptimumPool.Authentication.Controller;

import com.OptimumPool.Authentication.Exception.UserAlreadyExist;
import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Service.JwtService;
import com.OptimumPool.Authentication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User u) {
        try {
            return new ResponseEntity<>(userService.register(u), HttpStatus.CREATED);
        } catch (UserAlreadyExist e) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // FIXED: login now takes a DTO, not User entity directly
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            Map<String, String> result = userService.login(
                    credentials.get("username"),
                    credentials.get("password")
            );
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    // FIXED: username extracted from JWT, not from a shared instance field
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        return new ResponseEntity<>(userService.getProfile(username), HttpStatus.OK);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody User updates) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        return new ResponseEntity<>(userService.updateUser(username, updates), HttpStatus.OK);
    }

    @DeleteMapping("/profile/delete")
    public ResponseEntity<?> deleteProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        userService.deleteUser(username);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("Auth UP", HttpStatus.OK);
    }
}