package com.epam.esm;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.DefaultCertificateService;
import com.epam.esm.mapper.CertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {ServiceExceptionTest.class})
@ExtendWith(SpringExtension.class)
class ServiceExceptionTest {
    private final CertificateMapper mapper = CertificateMapper.getInstance();
    private static  CertificateDao certificateDao;
    private static CertificateService certificateService;
    private static final String message = "Service Exception";
    private static Exception cause;

    @BeforeEach
    void setUp() {
        cause = new Exception("Cause Exception");
        certificateDao = mock(CertificateDao.class);
        certificateService = new DefaultCertificateService(certificateDao, mapper);
    }

    @Test
    @DisplayName("Creating ServiceException with message")
    void createServiceExceptionWithMessage() {
        ServiceException exception = new ServiceException(message);
        assertThrows(ServiceException.class, () -> {
            throw exception;
        }, message);
    }

    @Test
    @DisplayName("Creating ServiceException with message and cause")
    void createServiceExceptionWithMessageAndCause() {
        ServiceException exception = new ServiceException(message, cause);
        assertThrows(ServiceException.class, () -> {
            throw exception;
        }, message);
    }

    @Test
    @DisplayName("Creating ServiceException with cause")
    void createServiceExceptionWithCause() {
        Exception cause = new Exception(message);
        ServiceException exception = new ServiceException(cause);
        assertThrows(ServiceException.class, () -> {
            throw exception;
        }, cause.getMessage());
    }
}
