package com.epam.esm.service;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<User> {
    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}