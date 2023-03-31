package com.epam.esm.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringJUnitConfig(AppConfig.class)
//class AppConfigTest {
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.driverClassName}")
//    private String driverClassName;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//
//    @ParameterizedTest
//    @CsvSource({
//            "reddit_5qs5_user, org.postgresql.Driver, " +
//                    "9pnjM54Tkgtiqwdfq20yaSXN4YNNEBuQ, " +
//                    "jdbc:postgresql://oregon-postgres.render.com:5432/reddit_5qs5"
//    })
//    @DisplayName("Test reading properties from application.properties")
//    void testProperties(String username, String driverClassName, String password, String url) {
//        assertEquals(username, this.username);
//        assertEquals(driverClassName, this.driverClassName);
//        assertEquals(password, this.password);
//        assertEquals(url, this.url);
//    }
//}