package com.epam.esm.mapper;

import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CertificateMapper.class})
public interface UserMapper {

//    @Mapping(target = "certificates", source = "certificates", qualifiedByName = "toCertificateDtoList")
    UserDto toDto(User user);

    User toEntity(UserDto dto);

//    List<UserDto> toDtoList(List<User> users);

}
