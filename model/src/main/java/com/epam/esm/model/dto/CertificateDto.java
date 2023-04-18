package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CertificateDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @JsonFormat(timezone = "GMT+03:00", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp createDate;
    @JsonFormat(timezone = "GMT+03:00", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp lastUpdateDate;
    private int duration;
    private Set<TagDto> tags;

    @Override
    public String toString() {
        return String.format(
                "{id=%d, name='%s', description='%s', price=%s, duration=%d, tags=%s}",
                id, name, description, price, duration, tags);
    }
}
