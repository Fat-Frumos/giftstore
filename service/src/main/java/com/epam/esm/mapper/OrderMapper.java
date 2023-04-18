package com.epam.esm.mapper;

import com.epam.esm.model.domain.Order;
import com.epam.esm.model.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    public OrderDto toDto(Order order);
}
