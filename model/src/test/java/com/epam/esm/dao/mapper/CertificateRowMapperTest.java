package com.epam.esm.dao.mapper;

import com.epam.esm.domain.Certificate;
import com.epam.esm.mapper.CertificateRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CertificateRowMapperTest {

    private CertificateRowMapper rowMapper;
    ResultSet resultSet = mock(ResultSet.class);

    @BeforeEach
    public void setUp() {
        rowMapper = new CertificateRowMapper();
    }

    @Test
    @DisplayName("Test mapRow with valid ResultSet")
    void testMapRowWithValidResultSet() throws SQLException {

        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Certificate");
        when(resultSet.getString("description")).thenReturn("Description");
        when(resultSet.getInt("duration")).thenReturn(30);
        when(resultSet.getTimestamp("create_date")).thenReturn(Timestamp.from(Instant.now()));
        when(resultSet.getTimestamp("last_update_date")).thenReturn(Timestamp.from(Instant.now()));
        Certificate certificate = rowMapper.mapRow(resultSet, 0);

        assertEquals(1L, Objects.requireNonNull(certificate).getId());
        assertEquals("Certificate", certificate.getName());
        assertEquals("Description", certificate.getDescription());
        assertEquals(30, certificate.getDuration());
        assertEquals(Instant.now().getEpochSecond(), certificate.getCreateDate().getEpochSecond());
        assertEquals(Instant.now().getEpochSecond(), certificate.getLastUpdateDate().getEpochSecond());
    }

    @Test
    @DisplayName("Test mapRow with null create_date")
    void testMapRowWithNullCreateDate() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn(null);
        when(resultSet.getString("description")).thenReturn("Desc1");
        when(resultSet.getInt("duration")).thenReturn(30);
        when(resultSet.getTimestamp("create_date")).thenReturn(null);
        when(resultSet.getTimestamp("last_update_date")).thenReturn(Timestamp.from(Instant.now()));

        CertificateRowMapper mapper = new CertificateRowMapper();
        assertThrows(NullPointerException.class, () -> mapper.mapRow(resultSet, 0));
    }
}
