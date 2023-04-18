package com.epam.esm.api.config;

import com.epam.esm.api.controller.CertificateController;
import com.epam.esm.model.dto.CertificateDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateAssembler implements RepresentationModelAssembler<CertificateDto, EntityModel<CertificateDto>> {

    @NonNull
    @Override
    public EntityModel<CertificateDto> toModel(@NonNull CertificateDto dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(CertificateController.class).getById(dto.getId())).withSelfRel(),
                linkTo(methodOn(CertificateController.class).update(dto)).withRel("update"));
    }

    public PagedModel<EntityModel<CertificateDto>> toModel(Page<CertificateDto> page) {
        return PagedModel.of(
                page.getContent()
                        .stream()
                        .map(this::toModel)
                        .collect(toList()),
                new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements()),
                linkTo(methodOn(CertificateController.class).getAll(null)).withSelfRel());
    }
}
