package com.epam.esm.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserDto implements Serializable {

    private Long id;
    private String username;
    private String email;
}
