package com.epam.esm.model.domain;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class Criteria {
    private final Pageable pageable;
    private final String name;
    private final String tagName;

    public Criteria() {
        this.pageable = PageRequest.of(0, 10);
        this.name = "";
        this.tagName = "";
    }
}