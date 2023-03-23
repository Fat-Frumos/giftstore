package com.epam.esm.dao.mapper;

import com.epam.esm.domain.Certificate;
import com.epam.esm.dto.CertificateDto;
import org.springframework.stereotype.Component;

@Component
public class CertificateDtoMapper {
    public CertificateDto toDto(Certificate certificate) {
        return CertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .createDate(certificate.getCreateDate())
                .lastUpdateDate(certificate.getLastUpdateDate())
                .description(certificate.getDescription())
                .duration(certificate.getDuration())
                .build();
    }
}
