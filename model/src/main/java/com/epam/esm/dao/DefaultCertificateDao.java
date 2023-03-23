package com.epam.esm.dao;

import com.epam.esm.dao.mapper.CertificateRowMapper;
import com.epam.esm.domain.Certificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.CertificateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.mapper.QueriesContext.*;

@Repository
@RequiredArgsConstructor
public class DefaultCertificateDao implements CertificateDao {

    private final JdbcTemplate jdbcTemplate;
    private final CertificateRowMapper certificateRowMapper;

    @Override
    public Optional<Certificate> getById(Long id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(GET_BY_ID, new Object[]{id},
                        certificateRowMapper));
    }

    @Override
    public Optional<Tag> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Certificate> getAll() {
        return jdbcTemplate.query(
                GET_ALL_CERTIFICATE,
                certificateRowMapper);
    }

    @Override
    public Certificate save(Certificate giftCertificates) {
        jdbcTemplate.update(INSERT_CERTIFICATE,
                giftCertificates.getName(),
                giftCertificates.getDescription(),
                Timestamp.from(giftCertificates.getCreateDate()),
                Timestamp.from(giftCertificates.getLastUpdateDate()),
                giftCertificates.getDuration());
        giftCertificates.setId((System.currentTimeMillis() >> 48) & 0x0FFF);
        return giftCertificates;
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }


    @Override
    public CertificateDto update(Certificate certificate) {
        return new CertificateDto();
    }
}
