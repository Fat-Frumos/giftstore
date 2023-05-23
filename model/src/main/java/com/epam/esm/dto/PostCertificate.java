package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class PostCertificate extends RepresentationModel<PostCertificate> {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    @JsonFormat(timezone = "GMT+03:00", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp createDate;
    @JsonFormat(timezone = "GMT+03:00", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp lastUpdateDate;
}
