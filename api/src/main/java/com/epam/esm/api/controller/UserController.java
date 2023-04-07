package com.epam.esm.api.controller;

import com.epam.esm.api.service.UserService;
import com.epam.esm.core.model.domain.User;
import com.epam.esm.core.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable final Long id) {
        return new ResponseEntity<>(
                userService.getUserById(id),
                HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<>(
                userService.getAll(),
                HttpStatus.OK);
    }
}
