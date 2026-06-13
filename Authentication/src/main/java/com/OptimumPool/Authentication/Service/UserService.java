package com.OptimumPool.Authentication.Service;

import com.OptimumPool.Authentication.Exception.UserAlreadyExist;
import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // FIXED: no more instance field for username (was a concurrency bug)
    public User register(User u) throws UserAlreadyExist {
        if (repo.existsByUsername(u.getUsername())) {
            throw new UserAlreadyExist();
        }
        // Hash password before saving — never store plaintext
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return repo.save(u);
    }

    public Map<String, String> login(String username, String rawPassword) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole());
        return response;
    }

    public User getProfile(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String username, User updates) {
        User existing = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (updates.getPhone() != 0) existing.setPhone(updates.getPhone());
        if (updates.getPassword() != null && !updates.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updates.getPassword()));
        }
        return repo.save(existing);
    }

    public void deleteUser(String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        repo.delete(user);
    }
}