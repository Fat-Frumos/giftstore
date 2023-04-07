package com.epam.esm.api.service;

import com.epam.esm.core.model.domain.User;
import com.epam.esm.core.model.dto.UserDto;
import com.epam.esm.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<User> {

    private final UserRepository userRepository;

    public UserDto getUserById(long id) {
        return new UserDto(id, "Jet Lee", "jet@i.ua");
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()))
                .collect(toList());
    }
}
