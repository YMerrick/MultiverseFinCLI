package com.fincore.app.application.accounts;

import com.fincore.app.domain.shared.AuthException;
import com.fincore.app.domain.user.User;
import com.fincore.app.domain.user.UserRepo;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;

    public void register(UUID userId, String firstName, String lastName) {
        if (userRepo.getById(userId).isEmpty()) throw new AuthException("User already exists");
        User newUser = User.createUser(userId, firstName, lastName);
        userRepo.save(newUser);
    }

    public User getUser(UUID userId) {
        return userRepo.getById(userId).orElseThrow();
    }
}
