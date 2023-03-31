package com.epam.esm;

import com.epam.esm.domain.Certificate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


class CertificateControllerTest {

    private String baseUrl = "http://localhost:8080/certificates";
    private final RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<Certificate> responses;

}
