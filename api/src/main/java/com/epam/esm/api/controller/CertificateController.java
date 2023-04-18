package com.epam.esm.api.controller;

import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/certificates")
public class CertificateController {
    private final CertificateService certificateService;
    private final PagedResourcesAssembler<CertificateDto> assembler;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CertificateDto getById(
            @PathVariable final Long id) {
        return certificateService.getById(id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PagedModel<EntityModel<CertificateDto>> getAll(Pageable pageable) {
        return assembler.toModel(
                certificateService.getAll(pageable));
    }

    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    public final CertificateDto update(
            @RequestBody final CertificateDto dto) {
        return certificateService.update(dto);
    }

    @ResponseStatus(CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public final CertificateDto create(
            @RequestBody final CertificateDto dto) {
        return certificateService.save(dto);
    }

    @ResponseStatus(OK)
    @DeleteMapping(value = "/{id}")
    public final void delete(
            @PathVariable final Long id) {
        certificateService.delete(id);
    }
}
