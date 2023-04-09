package com.epam.esm.mapper;

import com.epam.esm.model.dto.BaseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityMapper<E, D extends BaseDto> {

    D toDto(E e);

    E toEntity(D d);
}
