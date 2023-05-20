package com.epam.esm.service;

import com.epam.esm.criteria.Criteria;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagMapper tagMapper;
    private static final String MESSAGE = "Tag not found by";

    @Override
    @Transactional(readOnly = true)
    public TagDto getById(final Long id) {
        Objects.requireNonNull(id, "Id should be not null");
        Tag tag = tagDao.getById(id)
                .orElseThrow(() -> new TagNotFoundException(
                        String.format("%s id: %s", MESSAGE, id)));
        return tagMapper.toDto(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto getByName(final String name) {
        Objects.requireNonNull(name, "Name should be not null");
        Tag tag = tagDao.getByName(name)
                .orElseThrow(() -> new TagNotFoundException(
                        String.format("%s name: %s", MESSAGE, name)));
        return tagMapper.toDto(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getAll(Criteria criteria) {
        return tagDao.getAll(criteria)
                .stream()
                .map(tagMapper::toDto)
                .collect(toList());  //TODO
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TagDto save(final TagDto tagDto) {
        Tag saved = tagDao.save(tagMapper.toEntity(tagDto));
        return tagMapper.toDto(saved);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(final Long id) {
        Objects.requireNonNull(id, "Id should be not null");
        tagDao.delete(id);
    }
}
