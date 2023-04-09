package com.epam.esm.api.controller;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
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
                OK);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<>(
                userService.getAll(), OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(
            @RequestBody final UserDto userDto) {
        return new ResponseEntity<>(
                userService.save(userDto), OK);
    }

    @DeleteMapping(value = "/{id}")
    public final ResponseEntity<Void> delete(
            @PathVariable("id") final Long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
}
