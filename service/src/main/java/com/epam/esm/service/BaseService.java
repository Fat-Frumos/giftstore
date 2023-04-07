package com.epam.esm.service;

import com.epam.esm.model.domain.BaseEntity;

import java.util.List;
public interface BaseService<T extends BaseEntity> {
    List<T> getAll();
}
