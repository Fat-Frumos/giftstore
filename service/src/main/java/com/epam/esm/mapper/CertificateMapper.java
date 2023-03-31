package com.epam.esm.mapper;

import com.epam.esm.domain.Certificate;
import com.epam.esm.dto.CertificateDto;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CertificateMapper implements EntityMapper<Certificate, CertificateDto> {
    public static final CertificateMapper mapper = new CertificateMapper();

    public static CertificateMapper getInstance() {
        return mapper;
    }

    private CertificateMapper() {
    }

    @Override
    public CertificateDto toDto(Certificate certificate) {
        if (certificate == null) {
            throw new NullPointerException("Certificate must not be null");
        }
        return CertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .createDate(certificate.getCreateDate())
                .lastUpdateDate(Instant.now())
                .duration(certificate.getDuration())
                .build();
    }

    @Override
    public Certificate toEntity(CertificateDto certificateDto) {
        if (certificateDto == null) {
            throw new NullPointerException("Certificate Dto must not be null");
        }
        return Certificate.builder()
                .id(certificateDto.getId())
                .name(certificateDto.getName())
                .description(certificateDto.getDescription())
                .price(certificateDto.getPrice())
                .createDate(certificateDto.getCreateDate())
                .lastUpdateDate(certificateDto.getLastUpdateDate())
                .duration(certificateDto.getDuration())
                .build();
    }
}