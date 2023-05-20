package com.epam.esm.service;

import com.epam.esm.criteria.Criteria;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Set;

public interface CertificateService {
    CertificateDto getById(Long id);

    List<CertificateDto> getAll(Criteria criteria);

    CertificateDto getByName(String name);

    void delete(Long id);

    CertificateDto update(CertificateDto dto);

    List<CertificateDto> getAllWithoutTags(Criteria criteria);

    CertificateDto save(CertificateDto dto);

    List<TagDto> findTagsByCertificateId(Long id);

    List<CertificateDto> findCertificatesByTags(List<String> tagNames);

    List<CertificateDto> getCertificatesByUserId(Long userId);

    Set<CertificateDto> getByIds(Set<Long> ids);
}
