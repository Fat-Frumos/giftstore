package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.domain.Certificate;
import com.epam.esm.dto.CertificateDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping(value = "/{id}")
    public CertificateDto getOne(
            @PathVariable Long id) {
        return certificateService.getById(id);
    }

    @GetMapping
    @ResponseBody
    public List<CertificateDto> getAll() {
        return certificateService.getAll();
    }

    @PutMapping(value = "/{id}")
    public Certificate update(
            @PathVariable("id") Long id,
            @RequestBody CertificateDto certificateDto) {
        certificateDto.setId(id);
        return certificateService.update(certificateDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(
            @PathVariable("id") Long id) {
        certificateService.delete(id);
    }
}
