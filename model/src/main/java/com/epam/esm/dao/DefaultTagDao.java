package com.epam.esm.dao;

import com.epam.esm.dao.mapper.TagRowMapper;
import com.epam.esm.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.dao.mapper.QueriesContext.*;

@Repository
@RequiredArgsConstructor
public class DefaultTagDao implements TagDao {

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    @Override
    public Optional<Tag> getById(Long id) {
        return Optional.of(Objects.requireNonNull(
                jdbcTemplate.queryForObject(GET_TAG_BY_ID,
                        new Object[]{id}, tagRowMapper)));
    }

    @Override
    public Optional<Tag> getByName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                GET_BY_NAME, new Object[]{name}, tagRowMapper));
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(GET_ALL_TAGS, tagRowMapper);
    }

    @Override
    public Tag save(Tag tag) {
        // TODO
        return new Tag();
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(DELETE_TAG, id);
    }
}
