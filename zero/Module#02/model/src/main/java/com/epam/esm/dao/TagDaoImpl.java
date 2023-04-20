package com.epam.esm.dao;

import com.epam.esm.dto.CertificateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.dao.QueriesContext.*;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    @Override
    public final Optional<Tag> getById(final Long id) {
        Tag tag = jdbcTemplate.queryForObject(
                GET_TAG_BY_ID,
                new Object[]{id},
                tagRowMapper);
        return tag == null
                ? Optional.empty()
                : Optional.of(tag);
    }

    @Override
    public final Optional<Tag> getByName(final String name) {
        List<Tag> tags = jdbcTemplate.query(
                String.format("%s'%s'", GET_BY_TAG_NAME, name),
                tagRowMapper);
        return tags.isEmpty()
                ? Optional.empty()
                : Optional.of(tags.get(0));
    }

    @Override
    public final List<Tag> getAll() {
        return jdbcTemplate.query(GET_ALL_TAGS, tagRowMapper);
    }

    @Override
    public final Long save(final Tag tag) {
        long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        jdbcTemplate.update(INSERT_TAG, id, tag.getName());
        return id;
    }

    @Override
    public final void delete(final Long id) {
        jdbcTemplate.update(DELETE_TAG_REF, id);
        jdbcTemplate.update(DELETE_TAG, id);
    }

    @Override
    public Set<Tag> saveAllTags(Set<Tag> tags) {
        return tags.stream()
                .collect(Collectors.toMap(tag -> tag,
                        this::save, (a, b) -> b))
                .keySet();
    }

    @Override
    public void saveAllRef(Set<CertificateTag> certificateTags) {
        certificateTags.forEach(certificateTag ->
                jdbcTemplate.update(INSERT_REF,
                        certificateTag.getCertificateId(),
                        certificateTag.getTagId()));
    }
}
