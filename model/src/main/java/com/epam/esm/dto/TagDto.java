package com.epam.esm.dto;

import com.epam.esm.domain.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TagDto {

    private Long id;
    private String name;

    public TagDto(final Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
