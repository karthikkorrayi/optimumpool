package com.OptimumPool.Authentication.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;    // stored as BCrypt hash

    @Column(nullable = false)
    private String role;        // "OWNER" or "CUSTOMER"

    private long phone;

    public User() {}

    public User(String username, String password, String role, long phone) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.phone = phone;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public long getPhone() { return phone; }
    public void setPhone(long phone) { this.phone = phone; }
}