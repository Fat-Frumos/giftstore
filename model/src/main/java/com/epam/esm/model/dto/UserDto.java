package com.epam.esm.model.dto;

import com.epam.esm.model.entity.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserDto implements Serializable {

    private Long id;
    private String username;
    private String email;
    private Set<Certificate> certificates;
}
