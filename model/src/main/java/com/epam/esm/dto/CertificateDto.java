package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static java.lang.String.format;

@Setter
@Getter
@Builder
public class CertificateDto {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd-hh:mm:ss.SSS";
    private static final String DATE_TIME_TIMEZONE = "UTC";

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @JsonFormat(pattern = DATE_TIME_PATTERN, timezone = DATE_TIME_TIMEZONE)
    private Instant createDate;
    @JsonFormat(pattern = DATE_TIME_PATTERN, timezone = DATE_TIME_TIMEZONE)
    private Instant lastUpdateDate;
    private int duration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TagDto> tags;

    @Override
    public String toString() {
        return format(
                "{id=%d, name='%s', description='%s', price=%s, duration=%d, tags=%s}",
                id, name, description, price, duration, tags);
    }
}
