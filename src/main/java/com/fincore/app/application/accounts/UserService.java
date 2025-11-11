package com.fincore.app.application.accounts;

import com.fincore.app.domain.user.User;
import com.fincore.app.domain.user.UserRepo;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;

    public void register(String firstName, String lastName) {
        User newUser = User.createUser(firstName, lastName);
        userRepo.save(newUser);
    }

    public User getUser(UUID userId) {
        return userRepo.getById(userId).orElseThrow();
    }
}
