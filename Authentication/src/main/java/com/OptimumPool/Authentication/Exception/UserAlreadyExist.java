package com.OptimumPool.Authentication.Exception;

public class UserAlreadyExist extends Exception {
    public UserAlreadyExist() {
        super("User with this username already exists");
    }
}