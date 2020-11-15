package com.example.paymybuddy.paymybuddy.service.impl;

import com.example.paymybuddy.paymybuddy.model.entity.User;
import com.example.paymybuddy.paymybuddy.repository.UserRepository;
import com.example.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        repository.delete(user);
    }

    @Override
    public User register(String email, String password) {
        if (repository.existsDistinctByEmailEquals(email)) {
            return null;
        } else {
            return repository.save(new User(email, password, 00.00));
        }
    }

    @Override
    @Transactional
    public void deleteByName(String email) {
        repository.deleteUserByEmailEquals(email);
    }

}
