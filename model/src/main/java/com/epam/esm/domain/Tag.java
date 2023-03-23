package com.epam.esm.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Tag implements BaseEntity {

    private Long id;
    private String name;
    private Set<Certificate> certificates = new HashSet<>();

}
