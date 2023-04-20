package com.epam.esm.api.controller;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(
            @PathVariable final Long id) {
        return userService.getById(id);
    }

    @GetMapping()
    public List<UserDto> getUsers() {
        return userService.getAll();
    }
}
