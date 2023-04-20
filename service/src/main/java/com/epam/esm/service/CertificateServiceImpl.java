package com.epam.esm.service;

import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.dao.CertificateRepository;
import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.entity.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateMapper mapper;
    private final CertificateRepository repository;
    private static final String MESSAGE = "Could not find certificate";

    @Override
    public CertificateDto getById(long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    public Certificate findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new CertificateNotFoundException(
                        String.format("%s with id %s", MESSAGE, id)));
    }

    @Override
    public List<CertificateDto> getAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(mapper::toDto)
                .collect(toList());
    }

    public Page<CertificateDto> getAll(Pageable pageable) {
        return repository
                .findAllBy(pageable)
                .map(mapper::toDto);
    }

    @Override
    public CertificateDto save(CertificateDto dto) {
        return mapper.toDto(
                repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public CertificateDto update(CertificateDto dto) {
        Certificate certificate = findById(dto.getId());
        if (dto.getName() != null) {
            certificate.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            certificate.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            certificate.setPrice(dto.getPrice());
        }
        if (dto.getDuration() != 0) {
            certificate.setDuration(dto.getDuration());
        }
        certificate.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        return mapper.toDto(repository.save(certificate));
    }
}
