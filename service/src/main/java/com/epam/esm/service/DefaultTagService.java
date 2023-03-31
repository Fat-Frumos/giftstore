package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "equations")
public class DefaultTagService implements TagService {

    private final TagDao tagDao;

    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public TagDto getById(final Long id)
            throws ServiceException {
        try {
            Tag tag = tagDao.getById(id);
            return new TagDto(tag);
        } catch (DaoException e) {
            throw new ServiceException(
                    "Certificate not found", e);
        }
    }

    @Override
    public TagDto getByName(String name) throws ServiceException {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getAll()
            throws ServiceException {
        try {
            return tagDao.getAll()
                    .stream()
                    .map(TagDto::new)
                    .collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean save(TagDto tag) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        return false;
    }
}
