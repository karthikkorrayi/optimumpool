package com.OptimumPool.Authentication.Service;

import com.OptimumPool.Authentication.Exception.UserAlreadyExist;
import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Repository.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IService{

    String un;
    @Autowired
    private UserRepo repo;

    @Override
    public User addUser(User u) throws UserAlreadyExist {
        if(!repo.findById(u.getUsername()).isEmpty())
        {
            throw new UserAlreadyExist();
        }
        User u1 = repo.save(u);
        return u1;
    }

    @Override
    public Map<String, String> login(User u) {
        Map<String, String> token = new HashMap<String, String>();
        un = u.getUsername();
        User u1 = repo.findByUsernameAndPassword(u.getUsername(), u.getPassword());
        if(u1!=null){
            token = getToken(u);
        }
        return token;
    }

    public Map<String, String> getToken(User u) {
        String jwtToken = Jwts.builder()
                .setSubject(u.getUsername())
                .setIssuedAt(new Date(0))
                .signWith(SignatureAlgorithm.HS256, "itcKey").compact();
        Map<String, String> jtoken = new HashMap<String, String>();
        jtoken.put("token" , jwtToken);
        return jtoken;
    }

    @Override
    public User getProfile() {
        return repo.findByUsername(un);
    }

    @Override
    public User getUpdate(User u) {
        Optional<User> existingUser = Optional.ofNullable(repo.findByUsername(un));
        User u1 = existingUser.get();
        u1.setPassword(u.getPassword());
        u1.setPhone(u.getPhone());
        User u2 = repo.save(u1);
        return u2;
    }

    @Override
    public boolean getDelete() {
        repo.deleteById(un);
        return true;
    }
}
