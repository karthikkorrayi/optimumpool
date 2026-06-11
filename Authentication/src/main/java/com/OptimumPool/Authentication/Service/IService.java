package com.OptimumPool.Authentication.Service;

import com.OptimumPool.Authentication.Exception.UserAlreadyExist;
import com.OptimumPool.Authentication.Model.User;

import java.util.Map;

public interface IService {
    public User addUser(User u) throws UserAlreadyExist;
    public Map<String, String> login(User u);
    public User getProfile();
    public User getUpdate(User u);
    public boolean getDelete();
}
