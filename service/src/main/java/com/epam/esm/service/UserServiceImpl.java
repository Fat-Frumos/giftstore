package com.epam.esm.service;

import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.dao.UserRepository;
import com.epam.esm.model.domain.User;
import com.epam.esm.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public UserDto getById(long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User not found with id %d", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return mapper.toDtoList(
                repository.findAll());
    }

    @Override
    @Transactional
    public UserDto save(final UserDto dto) {
        return mapper.toDto(
                repository.save(
                        findById(dto.getId())));
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        repository.deleteById(
                findById(id).getId());
    }
}
