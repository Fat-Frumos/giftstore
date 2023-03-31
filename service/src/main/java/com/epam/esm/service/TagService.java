package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface TagService extends BaseService<TagDto> {

    @Override
    TagDto getById(Long id) throws ServiceException;

    @Override
    TagDto getByName(String name) throws ServiceException;

    @Override
    List<TagDto> getAll() throws ServiceException;

    @Override
    boolean save(TagDto tag) throws ServiceException;

    @Override
    boolean delete(Long id) throws ServiceException;
}
