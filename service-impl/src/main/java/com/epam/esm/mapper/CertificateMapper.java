package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PostCertificate;
import com.epam.esm.entity.Certificate;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TagMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CertificateMapper {
    @Mapping(target = "tags", source = "tags",
            qualifiedByName = "toTagSet")
    Certificate toEntity(CertificateDto certificateDto);

    Certificate toEntity(PostCertificate certificateDto);

    @Mapping(target = "tags", source = "tags",
            qualifiedByName = "toTagDtoSet")
    CertificateDto toDto(Certificate certificate);

    @Named("certificateDtos")
    @Mapping(target = "tags", source = "tags",
            qualifiedByName = "toTagDtoSet")
    List<CertificateDto> toDtoList(List<Certificate> certificates);

    @Mapping(target = "tags", source = "tags",
            qualifiedByName = "toTagSet")
    List<Certificate> toListEntity(List<CertificateDto> certificateDtos);

    @Named("toCertificateSlimDto")
    @Mapping(target = "tags", source = "tags")
    List<PostCertificate> toCertificateSlimDto(List<Certificate> certificateList);
    CertificateDto toDto(PostCertificate postCertificate);
}
