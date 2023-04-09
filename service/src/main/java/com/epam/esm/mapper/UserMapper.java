package com.epam.esm.mapper;

import com.epam.esm.model.domain.User;
import com.epam.esm.model.dto.UserDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class UserMapper implements EntityMapper<User, UserDto> {
    public static final UserMapper MAPPER = new UserMapper();

    public static UserMapper getInstance() {
        return MAPPER;
    }

    @Override
    public UserDto toDto(final User user) {
        if (user == null) {
            throw new NullPointerException("User must not be null");
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User toEntity(final UserDto userDto) {
        if (userDto == null) {
            throw new NullPointerException("User must not be null");
        }
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .build();
    }
}
