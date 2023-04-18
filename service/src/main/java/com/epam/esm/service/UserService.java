package com.epam.esm.service;

import com.epam.esm.model.domain.User;
import com.epam.esm.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getById(long id);

    User findById(long id);

    List<UserDto> getAll();

    UserDto save(UserDto userDto);

    void delete(Long id);
}
