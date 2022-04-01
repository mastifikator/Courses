package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.UserRepository;
import com.mts.teta.courses.domain.User;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLister {
    private final UserRepository userRepository;

    @Autowired
    public UserLister(UserRepository repository) {
        this.userRepository = repository;
    }

    public User userById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User createUser(UserRequestToCreate request) {
        User user = new User(request.getUsername());
        userRepository.save(user);
        return user;
    }

    public User updateUser(Long id, UserRequestToUpdate request) {
        User user = userRepository.getById(id);
        user.setUsername(request.getUsername());
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
