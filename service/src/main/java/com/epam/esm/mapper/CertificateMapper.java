package com.epam.esm.mapper;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.dto.CertificateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TagMapper.class})
public interface CertificateMapper {

    @Mapping(target = "tags", source = "tags", qualifiedByName = "toTagSet")
    Certificate toEntity(CertificateDto dto);

    @Mapping(target = "tags", source = "tags", qualifiedByName = "toTagDtoSet")
    CertificateDto toDto(Certificate certificate);

//    @Mapping(target = "tags", source = "tags", qualifiedByName = "toTagDtoSet")
//    List<CertificateDto> toDtoList(Iterable<Certificate> certificates);

    @Mapping(target = "tags", source = "tags", qualifiedByName = "toTagSet")
    List<Certificate> toListEntity(Iterable<CertificateDto> dtos);

//    @Mapping(target = "tags", source = "tags", qualifiedByName = "toTagDtoSet")
//    List<CertificateWithoutTags> toDtoWithoutTagsList(Iterable<Certificate> certificateList);

//    @Named("toCertificateList")
//    default List<Certificate> toCertificateList(Iterable<CertificateDto> dtos) {
//        return dtos == null ? new ArrayList<>()
//                : dtos.stream()
//                .map(this::toEntity)
//                .collect(toList());
//    }
//    @Named("toCertificateDtoList")
//    default List<CertificateDto> toCertificateDtoSet(Iterable<Certificate> certificates) {
//        return certificates == null ? new ArrayList<>() :
//                certificates.stream()
//                        .map(certificate -> this.toDto(certificate))
//                        .collect(Collectors.toSet());
//    }

    @Named("toCertificateSet")
    default Set<Certificate> toCertificateSet(Set<CertificateDto> certificateDtos) {
        return certificateDtos == null ? new HashSet<>() :
                certificateDtos.stream()
                        .map(certificateDto -> this.toEntity(certificateDto))
                        .collect(Collectors.toSet());
    }
}
