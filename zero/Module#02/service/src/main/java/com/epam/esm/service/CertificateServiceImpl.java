package com.epam.esm.service;

import com.epam.esm.criteria.Criteria;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificateTag;
import com.epam.esm.dto.CertificateWithoutTagDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateAlreadyExistsException;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private static final String MESSAGE = "Certificate not found";
    private final CertificateDao certificateDao;
    private final TagDao tagDao;
    private final CertificateMapper mapper;
    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public CertificateDto getById(final Long id) {
        Objects.requireNonNull(id, "Id should be not null");
        return mapper.toDto(certificateDao.getById(id)
                .orElseThrow(() -> new CertificateNotFoundException(
                        String.format("%s with id: %d", MESSAGE, id))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> getAll() {
        return mapper.toDtoList(
                certificateDao.getAll());
    }

    @Override
    @Transactional(readOnly = true)
    public CertificateDto getByName(final String name) {
        Objects.requireNonNull(name, "Name should be not null");
        return mapper.toDto(certificateDao.getByName(name)
                .orElseThrow(() -> new CertificateNotFoundException(
                        String.format("%s with name: %s", MESSAGE, name))));
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        Objects.requireNonNull(id, "Id should be not null");
        Objects.requireNonNull(getById(id), MESSAGE);
        certificateDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> getAllBy(final Criteria criteria) {
        return mapper.toDtoList(certificateDao.getAllBy(criteria));
    }

    @Override
    @Transactional
    public CertificateDto update(final CertificateDto dto) {
        if (certificateDao.getById(dto.getId()).isEmpty()) {
            throw new CertificateNotFoundException(
                    String.format("%s with id: %d", MESSAGE, dto.getId()));
        }
        Certificate certificate = mapper.toEntity(dto);
        certificateDao.update(certificate);
        CertificateDto savedDto = getById(dto.getId());
        Set<TagDto> tagDtos = dto.getTags();
        if (tagDtos != null && !tagDtos.isEmpty()) {
            Set<Tag> tags = tagDao.saveAllTags(tagDtos.stream()
                    .map(tagMapper::toEntity).collect(toSet()));

            Set<CertificateTag> certificateTags = tags.stream()
                    .map(tag -> CertificateTag.builder()
                            .certificateId(certificate.getId())
                            .tagId(tag.getId())
                            .build())
                    .collect(toSet());

            tagDao.saveAllRef(certificateTags);
            savedDto.setTags(tags.stream().map(tagMapper::toDto).collect(toSet()));
        }
        return savedDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateWithoutTagDto> getAllWithoutTags() {
        return mapper.toDtoWithoutTagsList(certificateDao.getAll());
    }

    @Override
    @Transactional
    public CertificateDto save(final CertificateDto dto) {
        if (certificateDao.getByName(dto.getName()).isPresent()) {
            throw new CertificateAlreadyExistsException(dto.getName());
        }
        if (dto.getTags() != null) {
            log.info(dto.getTags().toString());
        }
        return getById(certificateDao.save(mapper.toEntity(dto)));
    }
}
