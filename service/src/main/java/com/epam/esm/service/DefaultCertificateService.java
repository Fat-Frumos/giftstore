package com.epam.esm.service;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.CertificateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DefaultCertificateService implements CertificateService {

    private final CertificateDao certificateDao;
    private final CertificateMapper mapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable("certificates")
    public CertificateDto getById(final Long id)
            throws ServiceException {
        try {
            return mapper.toDto(certificateDao.getById(id));
        } catch (Exception e) {
            throw new ServiceException("Certificate not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable("certificates")
    public List<CertificateDto> getAll()
            throws ServiceException {
        try {
            return certificateDao.getAll().stream()
                    .map(mapper::toDto)
                    .collect(toList());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("certificates")
    public CertificateDto getByName(final String name)
            throws ServiceException {
        try {
            return mapper.toDto(certificateDao.getByName(name));
        } catch (Exception e) {
            throw new ServiceException(
                    "Certificate %d not found", e);
        }
    }


    @Override
    @Transactional
    @CacheEvict(value = "certificates", allEntries = true)
    public boolean delete(final Long id)
            throws ServiceException {
        try {
            return certificateDao.delete(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    @CachePut("certificates")
    public boolean update(
            final CertificateDto certificateDto)
            throws ServiceException {
        try {
            return certificateDao.update(
                    mapper.toEntity(certificateDto));
        } catch (Exception e) {
            throw new ServiceException(certificateDto.toString());
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "certificates", allEntries = true)
    public boolean save(CertificateDto certificateDto)
            throws ServiceException {
        try {
            return certificateDao.save(
                    mapper.toEntity(certificateDto));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
