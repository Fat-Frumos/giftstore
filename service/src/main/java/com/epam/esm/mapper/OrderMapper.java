package com.epam.esm.mapper;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface OrderMapper {
//    @Mapping(target = "certificates", source = "certificates", qualifiedByName = "toCertificateDtoList")

    @Mapping(source = "user.id", target = "user")
    OrderDto toDto(Order order);

    @Mapping(source = "user", target = "user.id")
    Order toEntity(OrderDto dto);
}
