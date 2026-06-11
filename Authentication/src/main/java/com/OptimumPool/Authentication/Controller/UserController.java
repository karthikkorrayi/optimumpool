package com.OptimumPool.Authentication.Controller;

import com.OptimumPool.Authentication.Exception.UserAlreadyExist;
import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService ser;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody User u) throws UserAlreadyExist {
        try{
            return new ResponseEntity<>(ser.addUser(u), HttpStatus.CREATED);
        }
        catch(UserAlreadyExist uae)
        {
            throw new UserAlreadyExist();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @PostMapping("login")
    public ResponseEntity<?> Login(@RequestBody User u){
        return new ResponseEntity<>(ser.login(u),HttpStatus.CREATED);
    }

    @GetMapping("login/profile")
    public ResponseEntity<?> getUserInfo(){
        return new ResponseEntity<>(ser.getProfile(),HttpStatus.OK);
    }

    @PutMapping("login/profile/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody User u){
        User ul = ser.getUpdate(u);
        return new ResponseEntity<>(ul,HttpStatus.OK);
    }

    @DeleteMapping("login/profile/delete")
    public String getDetails(){
        ser.getDelete();
        return "Deleted Successfully";
    }
}
