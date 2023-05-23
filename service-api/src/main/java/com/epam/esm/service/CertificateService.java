package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PostCertificate;
import com.epam.esm.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CertificateService {
    CertificateDto getById(Long id);

    Page<CertificateDto> getAll(Pageable pageable);

    CertificateDto getByName(String name);

    void delete(Long id);

    CertificateDto update(CertificateDto dto);

    Page<CertificateDto> getAllSlimTags(Pageable pageable);

    CertificateDto save(PostCertificate dto);

    List<TagDto> findTagsByCertificateId(Long id);

    Page<CertificateDto> findAllByTags(List<String> tagNames);

    Page<CertificateDto> getCertificatesByUserId(Long userId);

    Page<CertificateDto> getByIds(Set<Long> ids);
}
