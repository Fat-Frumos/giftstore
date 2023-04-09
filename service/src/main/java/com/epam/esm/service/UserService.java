package com.epam.esm.service;

import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.dao.UserRepository;
import com.epam.esm.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "users")
public class UserService implements BaseService<UserDto> {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Cacheable("users")
    @Transactional(readOnly = true)
    public UserDto getUserById(long id) {
        return userMapper.toDto(
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        String.format("Requested resource not found: %d", id))));
    }

    @Override
    @Cacheable("users")
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(toList());
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public UserDto save(final UserDto userDto) {
        return userMapper
                .toDto(userRepository
                        .save(userMapper.toEntity(userDto)));
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(final Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(
                    String.format("Requested resource not found: %d", id));
        }
    }
}
