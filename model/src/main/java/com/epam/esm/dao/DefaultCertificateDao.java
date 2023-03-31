package com.epam.esm.dao;

import com.epam.esm.mapper.CertificateRowMapper;
import com.epam.esm.domain.Certificate;
import com.epam.esm.exception.DaoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.epam.esm.mapper.QueriesContext.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultCertificateDao implements CertificateDao {

    private final JdbcTemplate jdbcTemplate;
    private final CertificateRowMapper certificateRowMapper;

    @Override
    public final Certificate getById(final Long id)
            throws DaoException {
        try {
            return jdbcTemplate.queryForObject(
                    GET_CERTIFICATE_BY_ID,
                    new Object[]{id},
                    certificateRowMapper);
        } catch (Exception e) {
            log.debug(GET_CERTIFICATE_BY_ID);
            throw new DaoException(e);
        }
    }

    @Override
    public final Certificate getByName(final String name)
            throws DaoException {
        try { // TODO
            return jdbcTemplate.queryForObject(
                    GET_CERTIFICATE_BY_NAME,
                    new Object[]{name},
                    certificateRowMapper);
        } catch (Exception e) {
            log.debug(GET_CERTIFICATE_BY_NAME);
            throw new DaoException(e);
        }
    }

    @Override
    public final List<Certificate> getAll()
            throws DaoException {
        try {
            return jdbcTemplate.query(
                    GET_ALL_CERTIFICATE,
                    certificateRowMapper);
        } catch (Exception e) {
            log.debug(GET_ALL_CERTIFICATE);
            throw new DaoException(e);
        }
    }

    @Override
    public final boolean save(final Certificate certificate)
            throws DaoException {
        try {
            return jdbcTemplate.update(INSERT_CERTIFICATE,
                    System.currentTimeMillis() >> 48 & 0x0FFF,
                    certificate.getName(),
                    certificate.getDescription(),
                    Timestamp.from(certificate.getCreateDate()),
                    Timestamp.from(certificate.getLastUpdateDate()),
                    certificate.getDuration()) == 1;
        } catch (Exception e) {
            log.debug(INSERT_CERTIFICATE);
            throw new DaoException("Failed to save certificate to the database", e);
        }
    }

    @Override
    public final boolean delete(final Long id)
            throws DaoException {
        try {
            return jdbcTemplate.update(
                    DELETE_CERTIFICATE, id) == 1;
        } catch (Exception e) {
            log.debug(DELETE_CERTIFICATE);
            throw new DaoException(e);
        }
    }

    @Override
    public final boolean update(
            final Certificate certificate)
            throws DaoException {
        try { // TODO update to DB,
            // TODO prices should be updated
            return false;
        } catch (Exception e) {
            log.debug(INSERT_CERTIFICATE);
            throw new DaoException(e);
        }
    }
}
