package com.epam.esm.service;

import com.epam.esm.model.dto.BaseDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BaseService<T extends BaseDto> {

    T getUserById(long id);

    List<T> getAll();

    T save(T t);

    @Transactional
    @CacheEvict(allEntries = true)
    void delete(
            Long id)
            throws RuntimeException;
}
