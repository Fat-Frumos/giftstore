package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CertificateDto extends RepresentationModel<PostCertificate> {
    private Long id;
    @Size(min = 1, max = 512)
    private String name;
    @Size(min = 1, max = 1024)
    private String description;
    @DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than 0.00")
    @DecimalMax(value = "10000.00", inclusive = false, message = "Price must be less than 10000.00")
    private BigDecimal price;
    @Min(value = 0, message = "Duration must be a positive number or zero.")
    @Max(value = 365, message = "Duration must be less than or equal to 365.")
    private Integer duration;
    @JsonFormat(timezone = "GMT+03:00", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp createDate;
    @JsonFormat(timezone = "GMT+03:00", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp lastUpdateDate;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TagDto> tags;
}
