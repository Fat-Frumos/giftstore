package com.epam.esm.mapper;

import com.epam.esm.model.domain.User;
import com.epam.esm.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CertificateMapper.class})
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);

    @Mapping(target = "tags", source = "certificates", qualifiedByName = "toCertificateDtoList")
    List<UserDto> toDtoList(Iterable<User> users);
}
