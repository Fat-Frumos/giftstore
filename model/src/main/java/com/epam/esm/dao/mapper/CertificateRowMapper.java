package com.epam.esm.dao.mapper;

import com.epam.esm.domain.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CertificateRowMapper implements RowMapper<Certificate> {
    private final String CERTIFICATE_ID = "c_id";
    private final String CERTIFICATE_NAME = "c_name";
    private final String CERTIFICATE_DESCRIPTION = "c_description";
    private final String CERTIFICATE_DURATION = "c_duration";
    private final String CERTIFICATE_CREATE_DATE = "c_create_date";
    private final String CERTIFICATE_LAST_UPDATE_DATE = "c_last_update_date";

    @Override
    public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
        return Certificate.builder()
                .id(resultSet.getLong(CERTIFICATE_ID))
                .name(resultSet.getString(CERTIFICATE_NAME))
                .description(resultSet.getString(CERTIFICATE_DESCRIPTION))
                .duration(resultSet.getInt(CERTIFICATE_DURATION))
                .createDate(resultSet.getTimestamp(CERTIFICATE_CREATE_DATE).toInstant())
                .lastUpdateDate(resultSet.getTimestamp(CERTIFICATE_LAST_UPDATE_DATE).toInstant())
                .build();
    }
}
