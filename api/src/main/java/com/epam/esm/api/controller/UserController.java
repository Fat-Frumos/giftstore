package com.epam.esm.api.controller;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto createUser(
            @RequestBody final UserDto userDto) {
        return userService.save(userDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(OK)
    public final void delete(
            @PathVariable final Long id) {
        userService.delete(id);
    }
}
