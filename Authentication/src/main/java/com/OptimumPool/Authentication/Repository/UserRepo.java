package com.OptimumPool.Authentication.Repository;

import com.OptimumPool.Authentication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String un);

}
