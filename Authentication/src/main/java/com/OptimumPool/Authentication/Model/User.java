package com.OptimumPool.Authentication.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    String username;
    String password;
    long phone;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public User(String username, String password, long phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public User() {
        super();
    }
}
