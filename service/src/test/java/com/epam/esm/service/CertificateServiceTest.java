package com.epam.esm.service;

import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.dao.CertificateRepository;
import com.epam.esm.model.domain.Certificate;
import com.epam.esm.model.dto.CertificateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CertificateServiceTest {

    public List<Certificate> certificates;
    private List<CertificateDto> certificateDtos;
    @Mock
    private CertificateRepository repository = mock(CertificateRepository.class);
    @Mock
    private CertificateMapper mapper = mock(CertificateMapper.class);
    @InjectMocks
    private CertificateService service;
    private final static Long ID = 1L;
    CertificateDto dto;

    @BeforeEach
    public void setUp() {
        service = new CertificateServiceImpl(mapper, repository);

        dto = CertificateDto.builder()
                .id(1L).name("Test Certificate")
                .description("Test description")
                .duration(10)
                .price(BigDecimal.valueOf(100))
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastUpdateDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        certificateDtos = Arrays.asList(
                CertificateDto.builder().id(1L).name("Certificate A").description("New Certificate 1").duration(10).price(new BigDecimal(10)).build(),
                CertificateDto.builder().id(2L).name("Certificate B").description("New Certificate 2").duration(20).price(new BigDecimal(20)).build(),
                CertificateDto.builder().id(3L).name("Certificate C").description("New Certificate 3").duration(30).price(new BigDecimal(30)).build()
        );
        certificates = Arrays.asList(
                Certificate.builder().id(1L).name("Certificate A").duration(10).description("New Certificate 1").price(new BigDecimal(10)).tags(new HashSet<>()).build(),
                Certificate.builder().id(2L).name("Certificate B").duration(20).description("New Certificate 2").price(new BigDecimal(20)).tags(new HashSet<>()).build(),
                Certificate.builder().id(3L).name("Certificate C").duration(30).description("New Certificate 3").price(new BigDecimal(30)).tags(new HashSet<>()).build()
        );
    }

    @ParameterizedTest
    @CsvSource({"1, Winter", "2, Summer", "3, Spring", "4, Autumn"})
    @DisplayName("Get certificate by id")
    void testGetById(Long id, String name) {
        CertificateDto certificateDto = CertificateDto.builder().id(id).name(name).build();
        Certificate certificate = Certificate.builder().id(id).name(name).build();
        when(repository.findById(id)).thenReturn(Optional.of(certificate));
        when(mapper.toDto(certificate)).thenReturn(certificateDto);
        CertificateDto result = service.getById(id);
        assertEquals(certificateDto, result);
    }

    @ParameterizedTest
    @CsvSource({"1, Winter", "2, Summer", "3, Spring", "4, Autumn"})
    @DisplayName("Get certificate by name")
    void getByName(Long id, String name) {
        CertificateDto certificateDto = CertificateDto.builder().id(id).name(name).build();
        Optional<Certificate> certificate = Optional.of(Certificate.builder().id(id).name(name).build());
        when(repository.findById(id)).thenReturn(certificate);
        when(mapper.toDto(certificate.get())).thenReturn(certificateDto);
        CertificateDto result = service.getById(id);
        assertEquals(certificateDto, result);
        verify(repository).findById(id);
        verify(mapper).toDto(certificate.get());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3"})
    @DisplayName("Delete certificate by id when found")
    void deleteFound(Long id) {
        CertificateDto certificateDto = CertificateDto.builder().id(id).build();
        Certificate certificate = Certificate.builder().id(id).build();
        when(repository.findById(id)).thenReturn(Optional.of(certificate));
        when(mapper.toDto(certificate)).thenReturn(certificateDto);
        service.delete(id);
        verify(repository).delete(certificate);
    }

    @ParameterizedTest
    @CsvSource({"5, Winter", "6, Summer", "7, Spring", "8, Autumn"})
    @DisplayName("Update certificate when not found")
    void updateNotFound(Long id, String name) {
        CertificateDto certificateDto = CertificateDto.builder().id(id).name(name).build();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class,
                () -> service.update(certificateDto), "CertificateNotFoundException"
        );
    }

    @Test
    @DisplayName("Test getting certificate by id")
    void testGetById() {
        Certificate certificate = Certificate.builder().id(ID).build();
        CertificateDto dto = CertificateDto.builder().id(ID).build();
        when(repository.findById(ID)).thenReturn(Optional.of(certificate));
        when(mapper.toDto(certificate)).thenReturn(dto);
        CertificateDto result = service.getById(ID);
        assertNotNull(result);
        assertEquals(ID, result.getId());
        verify(repository).findById(ID);
        verify(mapper).toDto(certificate);
    }

    @Test
    @DisplayName("Test Certificate Dto Builder")
    void testCertificateDtoBuilder() {
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("Test Certificate", dto.getName());
        assertEquals("Test description", dto.getDescription());
        assertEquals(Integer.valueOf(10), dto.getDuration());
        assertEquals(BigDecimal.valueOf(100), dto.getPrice());
        assertNotNull(dto.getCreateDate());
        assertNotNull(dto.getLastUpdateDate());
    }

    @Test
    @DisplayName("Should update certificate when certificate exists")
    void testUpdateShouldUpdateCertificate() {
        CertificateDto dto = certificateDtos.get(0);
        when(repository.findById(dto.getId())).thenReturn(Optional.of(certificates.get(0)));
        when(mapper.toEntity(dto)).thenReturn(certificates.get(0));
        when(repository.save(certificates.get(0))).thenReturn(certificates.get(0));
        Certificate updatedCertificate = repository.save(mapper.toEntity(dto));
        assertNotNull(updatedCertificate);
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).save(certificates.get(0));
        assertAll("Certificate fields should be updated properly",
                () -> assertEquals(updatedCertificate.getId(), dto.getId()),
                () -> assertEquals(updatedCertificate.getName(), dto.getName()),
                () -> assertEquals(updatedCertificate.getDescription(), dto.getDescription()),
                () -> assertEquals(updatedCertificate.getPrice(), dto.getPrice()),
                () -> assertEquals(updatedCertificate.getDuration(), dto.getDuration())
        );
    }

    private static Stream<CertificateDto> certificateDtoProvider() {
        return Stream.of(
                CertificateDto.builder().id(1L).name("Certificate A").description("New Certificate 1").price(new BigDecimal("10")).duration(10).build(),
                CertificateDto.builder().id(2L).name("Certificate B").description("New Certificate 2").price(new BigDecimal("20")).duration(20).build(),
                CertificateDto.builder().id(3L).name("Certificate C").description("New Certificate 3").price(new BigDecimal("30")).duration(30).build()
        );
    }

    @ParameterizedTest(name = "{index} => CertificateDto=''{0}''")
    @DisplayName("Test updating a certificate")
    @MethodSource("certificateDtoProvider")
    void testUpdateCertificate(CertificateDto dto) {
        int c = dto.getId().intValue() - 1;
        when(repository.findById(dto.getId())).thenReturn(Optional.of(certificates.get(c)));
        when(mapper.toEntity(dto)).thenReturn(certificates.get(c));
        when(repository.save(certificates.get(c))).thenReturn(certificates.get(c));
        Certificate updatedCertificate = repository.save(mapper.toEntity(dto));
        assertNotNull(updatedCertificate);
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).save(certificates.get(c));
        assertAll("Certificate fields should be updated properly",
                () -> assertEquals(updatedCertificate.getId(), dto.getId()),
                () -> assertEquals(updatedCertificate.getName(), dto.getName()),
                () -> assertEquals(updatedCertificate.getDescription(), dto.getDescription()),
                () -> assertEquals(updatedCertificate.getPrice(), dto.getPrice()),
                () -> assertEquals(updatedCertificate.getDuration(), dto.getDuration())
        );
    }

    @ParameterizedTest(name = "{index} => CertificateDto=''{0}''")
    @DisplayName("Test updating a non-existent certificate throws exception")
    @CsvSource({"100", "-1"})
    void testUpdateNonExistentCertificate(Long certificateId) {
        when(repository.findById(certificateId)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () ->
                service.update(CertificateDto.builder().id(certificateId).build()));
        verify(repository, times(1)).findById(certificateId);
        verify(repository, never()).save(any(Certificate.class));
        verify(mapper, never()).toDto(any(Certificate.class));
    }


    @Test
    @DisplayName("Should throw CertificateNotFoundException when certificate doesn't exist")
    void testUpdateShouldThrowCertificateNotFoundException() {
        when(repository.findById(certificateDtos.get(0).getId())).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> service.update(certificateDtos.get(0)));
        verify(repository, times(1)).findById(certificateDtos.get(0).getId());
        verify(mapper, never()).toEntity(certificateDtos.get(0));
        verify(repository, never()).save(any(Certificate.class));
    }

    @Test
    @DisplayName("Get certificate by non-existent name")
    void getCertificateByNonExistentName() {
        when(repository.findById(0L)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> service.findById(0L));
        verify(repository, times(1)).findById(0L);
    }

    @ParameterizedTest
    @CsvSource({"1, Winter", "2, Summer", "3, Spring", "4, Autumn"})
    @DisplayName("Delete certificate with invalid id throws CertificateNotFoundException")
    void deleteCertificateWithInvalidId(Long id, String name) {
        Certificate certificate = Certificate.builder().id(id).name(name).build();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> service.delete(id));
        verify(repository).findById(id);
        verify(repository, never()).delete(certificate);
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> service.delete(id));
        verify(repository, never()).delete(certificate);
    }

    @DisplayName("Test Get Certificate By Name")
    @ParameterizedTest(name = "Run {index}: name = {0}")
    @CsvSource({
            "1, Gift, description, 10, 30",
            "2, Certificate, description, 10, 30",
            "3, Java, description, 10, 30",
            "4, SQL, description, 10, 30",
            "5, Programming, description, 10, 30",
            "6, Spring, description, 10, 30"
    })

    void getCertificateByNames(Long id, String name, String description, BigDecimal price, int duration) {
        Certificate certificate = Certificate.builder()
                .id(id).description(description)
                .duration(duration).price(price).name(name).build();
        CertificateDto certificateDto = CertificateDto.builder()
                .id(id).name(name).description(description)
                .duration(duration).price(price).build();
        when(repository.findById(id)).thenReturn(Optional.of(certificate));
        when(mapper.toDto(certificate)).thenReturn(certificateDto);
        CertificateDto dto = mapper.toDto(service.findById(id));

        assertNotNull(certificateDto);
        assertEquals(name, certificateDto.getName());
        assertEquals("description", certificateDto.getDescription());
        assertEquals(new BigDecimal("10"), certificateDto.getPrice());
        assertEquals(30, certificateDto.getDuration());
        assertEquals(certificateDto, dto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(certificate);
    }
}
