package com.epam.esm.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long user;
    private BigDecimal price;
//    private List<CertificateDto> certificatesDtos;
}
