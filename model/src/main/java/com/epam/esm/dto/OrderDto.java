package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
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
public class OrderDto extends RepresentationModel<OrderDto> {
    @NotNull(message = "Id cannot be blank")
    private Long id;
    @NotNull(message = "User cannot be null")
    private UserSlimDto user;
    @NotNull(message = "Price cannot be null")
    @Size(min = 1, max = 10000)
    private BigDecimal cost;
    @JsonFormat(timezone = "GMT+03:00", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp orderDate;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CertificateDto> certificateDtos;
}
