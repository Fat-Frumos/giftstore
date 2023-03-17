package com.epam.esm.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class GiftCertificate {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private Integer duration;

    private Set<Tag> tags = new HashSet<>();

    @Override
    public String toString() {
        return "GiftCertificate";
    }
}