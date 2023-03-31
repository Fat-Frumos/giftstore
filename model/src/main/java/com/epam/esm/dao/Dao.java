package com.epam.esm.dao;

import com.epam.esm.domain.BaseEntity;
import com.epam.esm.exception.DaoException;

import java.util.List;

public interface Dao<T extends BaseEntity> {
    List<T> getAll() throws DaoException;

    T getById(Long id) throws DaoException;

    T getByName(String name) throws DaoException;

    boolean save(T entity) throws DaoException;

    boolean delete(Long id) throws DaoException;
}
