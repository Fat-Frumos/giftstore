package com.epam.esm.service;

import com.epam.esm.model.domain.Certificate;
import com.epam.esm.model.dto.CertificateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CertificateService {
    CertificateDto getById(long id);

    Certificate findById(long id);

    List<CertificateDto> getAll();

    CertificateDto save(CertificateDto dto);

    void delete(Long id);

    CertificateDto update(CertificateDto dto);

    Page<CertificateDto> getAll(Pageable pageable);
}
