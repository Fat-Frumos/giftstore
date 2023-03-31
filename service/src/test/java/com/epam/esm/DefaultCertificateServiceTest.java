package com.epam.esm;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.DefaultCertificateDao;
import com.epam.esm.mapper.CertificateRowMapper;
import com.epam.esm.domain.Certificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.DefaultCertificateService;
import com.epam.esm.mapper.CertificateMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultCertificateServiceTest {
    private final CertificateMapper mapper = CertificateMapper.getInstance();
    private static final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private static final CertificateRowMapper rowMapper = mock(CertificateRowMapper.class);
    @Mock
    private CertificateDao certificateDao = new DefaultCertificateDao(jdbcTemplate, rowMapper);
    @Mock
    private CertificateService certificateService = new DefaultCertificateService(certificateDao, mapper);
    private static List<Certificate> giftCertificateList;

    private static final String message = "An error occurred";
    private static List<Tag> tagList;
    private static final Long id = 1L;

    @BeforeEach
    public void setUp() {

        tagList = List.of(
                Tag.builder().id(1L).name("tag1").build(),
                Tag.builder().id(1L).name("tag1").build(),
                Tag.builder().id(1L).name("tag1").build(),
                Tag.builder().id(1L).name("tag1").build(),
                Tag.builder().id(1L).name("tag1").build()
        );

        giftCertificateList = List.of(
                Certificate.builder()
                        .id(1L)
                        .name("Gift2")
                        .duration(10)
                        .description("Certificate2")
                        .price(new BigDecimal(200))
                        .build(),

                Certificate.builder()
                        .id(2L)
                        .name("Gift2")
                        .duration(20)
                        .description("Certificate2")
                        .price(new BigDecimal(200))
                        .build(),

                Certificate.builder()
                        .id(3L)
                        .name("Gift2")
                        .description("Certificate2")
                        .duration(30)
                        .price(new BigDecimal(200))
                        .build()
        );

    }

    @Test
    void testGetAllSizeList() {
        when(certificateDao.getAll()).thenReturn(giftCertificateList);
        List<CertificateDto> actual = certificateService.getAll();
        assertEquals(giftCertificateList.size(), actual.size());
    }

    @Test
    @DisplayName("Test getById throws ServiceException when certificate does not exist")
    void testGetByIdThrowsServiceException() {
        when(certificateDao.getById(id)).thenReturn(null);
        assertThrows(ServiceException.class, () -> certificateService.getById(id));
    }

    @Test
    @DisplayName("Test getById with invalid id")
    void testGetByIdWithInvalidId() {
        assertThrows(ServiceException.class, () -> certificateService.getById(id));
    }

    @Test
    @DisplayName("Test getById with null id")
    void testGetByIdWithNullIdExpectedServiceException() {
        assertThrows(ServiceException.class, () -> certificateService.getById(null));
    }

    @Test
    void testGetById() throws ServiceException {
        when(certificateDao.getById(2L)).thenReturn(giftCertificateList.get(1));
        assertEquals(certificateService.getById(2L).toString(), giftCertificateList.get(1).toString());
    }

    @Test
    void testServiceException() {
        String expectedMessage = "Expected error message";
        try {
            throw new ServiceException(expectedMessage);
        } catch (ServiceException e) {
            Assertions.assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void testServiceExceptionWithMessageAndCause() {
        Throwable cause = new IllegalArgumentException("Invalid argument");
        ServiceException e = new ServiceException(message, cause);
        assertEquals(message, e.getMessage());
        assertEquals(cause, e.getCause());
    }

    @Test
    void testServiceExceptionThrownWithMessage() {
        try {
            throw new ServiceException(message);
        } catch (ServiceException ex) {
            assertEquals(message, ex.getMessage());
        }
    }
}
