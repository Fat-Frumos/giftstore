package com.epam.esm;

import com.epam.esm.config.H2JdbcConfig;
import com.epam.esm.domain.Certificate;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

//@TestPropertySource("classpath:application-dev.properties")
@AllArgsConstructor
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = H2JdbcConfig.class)
class CertificateControllerTest {

    private final JdbcTemplate jdbcTemplate;
    private final HttpHeaders headers;

    @BeforeEach
    void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

//    @Test
//    void testDatabase() {
//        List<Certificate> result = jdbcTemplate.queryForList("SELECT * FROM gift_certificates", Certificate.class);
//        assertNotNull(result);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    void testJdbcTemplate() {
//        jdbcTemplate.update("INSERT INTO tag (id, name) VALUES (?, ?)", 1, "Tag");
//        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tag", Integer.class);
//        assertEquals(1, count);
//    }
}
