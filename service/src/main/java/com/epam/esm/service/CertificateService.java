package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface CertificateService extends BaseService<CertificateDto> {
    @Override
    CertificateDto getById(Long id) throws ServiceException;

    @Override
    CertificateDto getByName(String name) throws ServiceException;

    @Override
    List<CertificateDto> getAll() throws ServiceException;

    boolean update(CertificateDto certificate) throws ServiceException;

    @Override
    boolean save(CertificateDto certificate) throws ServiceException;

    @Override
    boolean delete(Long id) throws ServiceException;
}
