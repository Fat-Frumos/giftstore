package com.epam.esm.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Tag {

    private Long id;

    private String name;

    private Set<GiftCertificate> giftCertificates = new HashSet<>();

    @Override
    public String toString() {
        return "Tag";
    }
}