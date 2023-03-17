package com.epam.esm.app;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void testDomain() {
        GiftCertificate certificate = new GiftCertificate();
        assertEquals("GiftCertificate", certificate.toString());
        Tag tag = new Tag();
        assertEquals("Tag", tag.toString());
    }
}
