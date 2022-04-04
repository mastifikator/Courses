package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.UserRepository;
import com.mts.teta.courses.domain.User;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserLister {
    private final UserRepository userRepository;

    @Autowired
    public UserLister(UserRepository repository) {
        this.userRepository = repository;
    }

    public User userById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User createUser(UserRequestToCreate request) {
        User user = new User(request.getUsername());
        userRepository.save(user);
        return user;
    }

    public User updateUser(Long userId, UserRequestToUpdate request) {
        User user = userRepository.getById(userId);
        user.setUsername(request.getUsername());
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
