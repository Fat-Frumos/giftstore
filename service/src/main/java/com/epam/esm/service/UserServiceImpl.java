package com.epam.esm.service;

import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.dao.UserRepository;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public UserDto getById(long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User not found with id %d", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .map(mapper::toDto)
                .collect(toList());
    }
}
