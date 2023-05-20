package com.epam.esm.service;

import com.epam.esm.criteria.Criteria;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto getById(Long id);

    TagDto getByName(String name);

    List<TagDto> getAll(Criteria criteria);

    TagDto save(TagDto tag);

    void delete(Long id);
}
