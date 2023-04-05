package com.epam.esm.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@Table("gift_certificates")
public class Criteria {

    @Column("sortOrder")
    SortOrder sortOrder;
    @Column("sortDirection")
    SortDirection sortDirection;
    @Column("name")
    String name;
    @Column("description")
    String description;
    @Column("tagName")
    String tagName;
    @Column("price")
    BigDecimal price;
    @Column("id")
    Long id;
    @Column("duration")
    Integer duration;
    @Column("date")
    Instant date;
}
