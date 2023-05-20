package com.epam.esm.service;

import com.epam.esm.criteria.Criteria;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserWithoutOrderDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private UserDao userDao;
    @Mock
    private CertificateDao certificateDao;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private CertificateMapper certificateMapper;
    @InjectMocks
    private OrderServiceImpl orderService;
    private final Criteria criteria = Criteria.builder().page(0).size(25).build();
    private final Long orderId = 1L;
    private final Long userId = 1L;
    Set<Long> certificateIds = new HashSet<>();
    private final Long id = 1L;
    private final Long id2 = 2L;
    private final User user = User.builder()
            .id(userId)
            .username("Olivia-Noah")
            .email("Olivia-Noah@gmail.com")
            .build();
    private final User user2 = User.builder()
            .id(2L)
            .username("Emma-Liam")
            .email("Emma-Liam@gmail.com")
            .build();

    private final UserWithoutOrderDto userDto2 = UserWithoutOrderDto.builder()
            .id(2L)
            .username("Emma-Liam")
            .email("Emma-Liam@gmail.com")
            .build();
    private final UserWithoutOrderDto userDto = UserWithoutOrderDto.builder()
            .id(userId)
            .username("Olivia-Noah")
            .email("Olivia-Noah@gmail.com")
            .build();
    private final Certificate certificate = Certificate.builder()
            .id(id)
            .name("Java")
            .description("Core")
            .price(BigDecimal.valueOf(100))
            .duration(50)
            .build();
    private final CertificateDto certificateDto = CertificateDto.builder()
            .id(id)
            .name("Java")
            .description("Core")
            .price(BigDecimal.valueOf(100))
            .duration(50)
            .build();

    private final Certificate certificate2 = Certificate.builder()
            .id(id2)
            .name("Spring")
            .description("Boot")
            .price(BigDecimal.valueOf(20))
            .duration(45)
            .build();

    private final CertificateDto certificateDto2 = CertificateDto.builder()
            .id(id2)
            .name("Spring")
            .description("Boot")
            .price(BigDecimal.valueOf(20))
            .duration(45)
            .build();
    private final List<Certificate> certificates = Arrays.asList(certificate, certificate2);
    private final List<CertificateDto> certificateDtos = Arrays.asList(certificateDto, certificateDto2);
    private final Order order = Order.builder().id(1L).user(user).certificates(Collections.singleton(certificate)).build();
    private final Order order2 = Order.builder().id(2L).user(user2).certificates(Collections.singleton(certificate2)).build();
    private final  OrderDto orderDto = OrderDto.builder().id(id).user(userDto).certificateDtos(Collections.singleton(certificateDto)).build();
    private final OrderDto orderDto2 = OrderDto.builder().id(id2).user(userDto2).certificateDtos(Collections.singleton(certificateDto2)).build();
    private final List<OrderDto> orderDtos = Arrays.asList(orderDto, orderDto2);
    private final List<Order> orders = Arrays.asList(order, order2);


    @BeforeEach
    public void setUp() {
        certificateIds.add(10L);
        certificateIds.add(20L);
        certificateIds.add(30L);
    }

    @Test
    @DisplayName("Get Order by ID")
    void testGetById() {
    
        when(orderDao.getById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto actualOrderDto = orderService.getById(orderId);

        assertEquals(orderDto.getId(), actualOrderDto.getId());
        assertEquals(orderDto.getUser().getEmail(), actualOrderDto.getUser().getEmail());
        assertEquals(orderDto.getUser().getUsername(), actualOrderDto.getUser().getUsername());
        assertEquals(orderDto.getCertificateDtos().size(), actualOrderDto.getCertificateDtos().size());
        assertEquals(orderDto.getCertificateDtos().iterator().next().getId(), actualOrderDto.getCertificateDtos().iterator().next().getId());

        verify(orderDao).getById(orderId);
        verify(orderMapper).toDto(order);
        verifyNoMoreInteractions(orderDao, orderMapper);
    }

    @Test
    @DisplayName("Get Order by ID - Order Not Found")
    void getByIdOrderNotFoundTest() {

        when(orderDao.getById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getById(orderId));

        verify(orderDao).getById(orderId);
        verifyNoMoreInteractions(orderDao, orderMapper);
    }

    @Test
    @DisplayName("Create Order")
    void createOrderTest() {

        Set<Long> certificateIds = Collections.singleton(id);
        Set<Certificate> certificates = Collections.singleton(certificate);

        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(certificateDao.findAllByIds(certificateIds)).thenReturn(certificates);
        when(orderDao.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto actualOrderDto = orderService.createOrder(userId, certificateIds);

        assertEquals(orderDto.getId(), actualOrderDto.getId());
        assertEquals(orderDto.getUser().getEmail(), actualOrderDto.getUser().getEmail());
        assertEquals(orderDto.getUser().getUsername(), actualOrderDto.getUser().getUsername());
        assertEquals(orderDto.getCertificateDtos().size(), actualOrderDto.getCertificateDtos().size());
        assertEquals(orderDto.getCertificateDtos().iterator().next().getId(), actualOrderDto.getCertificateDtos().iterator().next().getId());

        verify(userDao).getById(userId);
        verify(certificateDao).findAllByIds(certificateIds);
        verify(orderDao).save(any(Order.class));
        verify(orderMapper).toDto(order);
        verifyNoMoreInteractions(userDao, certificateDao, orderDao, orderMapper);
    }

    @Test
    @DisplayName("Save Order - UserNotFoundException")
    void saveOrderUserNotFoundException() {

        when(userDao.getById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> orderService.createOrder(userId, certificateIds));

        verify(userDao).getById(userId);
        verifyNoMoreInteractions(userDao, certificateDao, orderDao, orderMapper);
    }

    @Test
    @DisplayName("Save Order - CertificateNotFoundException")
    void saveOrderCertificateNotFoundExceptionTest() {

        when(userDao.getById(userId)).thenReturn(Optional.of(user));
        when(certificateDao.findAllByIds(certificateIds)).thenReturn(Collections.emptySet());

        assertThrows(CertificateNotFoundException.class,
                () -> orderService.createOrder(userId, certificateIds));

        verify(userDao).getById(userId);
        verify(certificateDao).findAllByIds(certificateIds);
        verifyNoMoreInteractions(userDao, certificateDao, orderDao, orderMapper);
    }

    @ParameterizedTest
    @DisplayName("Get User Orders")
    @CsvSource({
            "1, Olivia, Noah, Olivia-Noah@gmail.com, 10, Java, description, 10, 30",
            "2, Emma, Liam, Emma-Liam@gmail.com, 20, Certificate, description, 20, 45",
            "3, Charlotte, Oliver, Charlotte-Oliver@gmail.com, 30, Spring, description, 30, 60",
            "4, Amelia, Elijah, Amelia-Elijah@gmail.com, 40, SQL, description, 40, 75",
            "5, Ava, Leo, Ava-Leo@gmail.com, 50, Programming, description, 50, 90"
    })
    void getUserOrdersTest(Long orderId, String firstName, String lastName, String email,
                           long certificateId, String name, String description, BigDecimal price, int duration) {

        List<Order> expectedOrders = Collections.singletonList(order);
        List<OrderDto> expectedOrderDtos = Collections.singletonList(OrderDto.builder()
                .id(orderId)
                .user(UserWithoutOrderDto.builder()
                        .username(firstName + "-" + lastName)
                        .email(email)
                        .build())
                .certificateDtos(Collections.singleton(CertificateDto.builder()
                        .id(certificateId)
                        .name(name)
                        .description(description)
                        .price(price)
                        .duration(duration)
                        .build()))
                .build());

        when(orderDao.getUserOrders(user, criteria)).thenReturn(expectedOrders);
        when(orderMapper.toDtoList(expectedOrders)).thenReturn(expectedOrderDtos);

        List<OrderDto> actualOrderDtos = orderService.getUserOrders(user, criteria);

        assertEquals(expectedOrderDtos.size(), actualOrderDtos.size());
        assertEquals(expectedOrderDtos.get(0).getId(), actualOrderDtos.get(0).getId());
        assertEquals(expectedOrderDtos.get(0).getUser().getEmail(), actualOrderDtos.get(0).getUser().getEmail());

        verify(orderDao).getUserOrders(user, criteria);
        verify(orderMapper).toDtoList(expectedOrders);
        verifyNoMoreInteractions(orderDao, orderMapper);
    }

    @Test
    @DisplayName("Get User Order - OrderNotFoundException")
    void getUserOrderOrderNotFoundExceptionTest() {

        when(orderDao.getUserOrder(user, orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.getUserOrder(user, orderId));

        verify(orderDao).getUserOrder(user, orderId);
        verifyNoMoreInteractions(orderDao, orderMapper);
    }

    @Test
    @DisplayName("Test get user order")
    void getUserOrderTest(){

        when(orderDao.getUserOrder(user, orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto actualOrderDto = orderService.getUserOrder(user, orderId);

        assertEquals(orderDto, actualOrderDto);
        verify(orderDao).getUserOrder(user, orderId);
        verify(orderMapper).toDto(order);
    }

    @Test
    @DisplayName("Test get user order throws order not found exception")
    void getUserOrderThrowsOrderNotFoundExceptionTest() {

        when(orderDao.getUserOrder(user, orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getUserOrder(user, orderId));
        verify(orderDao).getUserOrder(user, orderId);
    }

    @ParameterizedTest
    @DisplayName("Get User Orders")
    @CsvSource({
            "1, Olivia, Noah, Olivia-Noah@gmail.com, 10, Java, description, 10, 30",
            "2, Emma, Liam, Emma-Liam@gmail.com, 20, Certificate, description, 20, 45",
            "3, Charlotte, Oliver, Charlotte-Oliver@gmail.com, 30, Spring, description, 30, 60",
            "4, Amelia, Elijah, Amelia-Elijah@gmail.com, 40, SQL, description, 40, 75",
            "5, Ava, Leo, Ava-Leo@gmail.com, 50, Programming, description, 50, 90"
    })
    void getAll(Long id, String firstName, String lastName, String email, long certificateId,
                String name, String description, BigDecimal price, int duration) {

        List<Order> expectedOrders = Collections.singletonList(order);
        List<OrderDto> expectedOrderDtos = Collections.singletonList(OrderDto.builder()
                .id(id)
                .user(UserWithoutOrderDto.builder()
                        .username(firstName + "-" + lastName)
                        .email(email)
                        .build())
                .certificateDtos(Collections.singleton(CertificateDto.builder()
                        .id(certificateId)
                        .name(name)
                        .description(description)
                        .price(price)
                        .duration(duration)
                        .build()))
                .build());

        when(orderDao.getAll(criteria)).thenReturn(expectedOrders);
        when(orderMapper.toDtoList(expectedOrders)).thenReturn(expectedOrderDtos);

        List<OrderDto> result = orderService.getAll(criteria);

        assertNotNull(result);
        assertEquals(expectedOrderDtos, result);
    }

    @ParameterizedTest
    @DisplayName("Get User Orders")
    @CsvSource({
            "1, Olivia, Noah, Olivia-Noah@gmail.com, 10, Java, description, 10, 30",
            "2, Emma, Liam, Emma-Liam@gmail.com, 20, Certificate, description, 20, 45",
            "3, Charlotte, Oliver, Charlotte-Oliver@gmail.com, 30, Spring, description, 30, 60",
            "4, Amelia, Elijah, Amelia-Elijah@gmail.com, 40, SQL, description, 40, 75",
            "5, Ava, Leo, Ava-Leo@gmail.com, 50, Programming, description, 50, 90"
    })
    void getAllByUserIdTest(Long id, String firstName, String lastName, String email, long certificateId,
                            String name, String description, BigDecimal price, int duration) {

        List<Order> expectedOrders = Collections.singletonList(order);
        List<OrderDto> expectedOrderDtos = Collections.singletonList(OrderDto.builder()
                .id(id)
                .user(UserWithoutOrderDto.builder()
                        .username(firstName + "-" + lastName)
                        .email(email)
                        .build())
                .certificateDtos(Collections.singleton(CertificateDto.builder()
                        .id(certificateId)
                        .name(name)
                        .description(description)
                        .price(price)
                        .duration(duration)
                        .build()))
                .build());

        when(orderDao.findOrdersByUserId(id)).thenReturn(expectedOrders);
        when(orderMapper.toDtoList(expectedOrders)).thenReturn(expectedOrderDtos);

        List<OrderDto> actualOrderDtos = orderService.getAllByUserId(id);
        assertEquals(expectedOrderDtos, actualOrderDtos);
        verify(orderDao).findOrdersByUserId(id);
        verify(orderMapper).toDtoList(expectedOrders);
    }

    @Test
    @DisplayName("Test find certificate by id throws certificate not found exception")
    void testFindCertificateByIdThrowsCertificateNotFoundException() {

        when(certificateDao.getById(userId)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () ->
                orderService.findCertificateById(userId));
        verify(certificateDao).getById(userId);
    }

    @Test
    @DisplayName("Test find Certificate By Id")
    void findCertificateByIdTest() {
        when(certificateDao.getById(id)).thenReturn(Optional.of(certificate));
        Certificate actualCertificate = orderService.findCertificateById(id);
        assertEquals(certificate, actualCertificate);
        verify(certificateDao).getById(id);
        verifyNoMoreInteractions(orderDao, orderMapper);
    }

    @Test
    @DisplayName("Get All Orders")
    void getAllOrdersTest() {

        when(orderDao.getAll(criteria)).thenReturn(orders);
        when(orderMapper.toDtoList(orders)).thenReturn(orderDtos);

        List<OrderDto> result = orderService.getAll(criteria);

        assertNotNull(result);
        assertEquals(orders.size(), result.size());

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            OrderDto orderDto = result.get(i);

            assertEquals(order.getId(), orderDto.getId());
            assertEquals(order.getUser().getUsername(), orderDto.getUser().getUsername());
            assertEquals(order.getUser().getEmail(), orderDto.getUser().getEmail());

            Set<Certificate> certificates = order.getCertificates();
            Set<CertificateDto> certificateDtos = orderDto.getCertificateDtos();

            assertEquals(certificates.size(), certificateDtos.size());

            Iterator<Certificate> certificateIterator = certificates.iterator();
            Iterator<CertificateDto> certificateDtoIterator = certificateDtos.iterator();

            while (certificateIterator.hasNext() && certificateDtoIterator.hasNext()) {
                Certificate certificate = certificateIterator.next();
                CertificateDto certificateDto = certificateDtoIterator.next();
                assertEquals(certificate.getId(), certificateDto.getId());
                assertEquals(certificate.getName(), certificateDto.getName());
                assertEquals(certificate.getDescription(), certificateDto.getDescription());
                assertEquals(certificate.getPrice(), certificateDto.getPrice());
                assertEquals(certificate.getDuration(), certificateDto.getDuration());
            }
        }
        verify(orderDao).getAll(criteria);
        verifyNoMoreInteractions(orderDao, orderMapper);
    }

    @Test
    @DisplayName("Get Most Used Tags")
    void getMostUsedTagsTest() {
        Tag tag = Tag.builder()
                .id(id)
                .name("Spring")
                .build();

        when(orderDao.getMostUsedTagBy(userId)).thenReturn(Optional.of(tag));

        Tag result = orderService.getMostUsedTags(userId);

        assertNotNull(result);
        assertEquals(tag.getId(), result.getId());
        assertEquals(tag.getName(), result.getName());

        verify(orderDao).getMostUsedTagBy(userId);
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    @DisplayName("Get Certificates by Tags")
    void getCertificatesByTagsTest() {
        List<String> tagNames = Arrays.asList("Java", "Spring");

        when(certificateDao.findByTagNames(tagNames)).thenReturn(certificates);
        when(certificateMapper.toDtoList(certificates)).thenReturn(certificateDtos);
        List<CertificateDto> result = orderService.getCertificatesByTags(tagNames);

        assertNotNull(result);
        assertEquals(certificates.size(), result.size());

        for (int i = 0; i < certificates.size(); i++) {
            Certificate certificate = certificates.get(i);
            CertificateDto certificateDto = result.get(i);

            assertEquals(certificate.getId(), certificateDto.getId());
            assertEquals(certificate.getName(), certificateDto.getName());
            assertEquals(certificate.getDescription(), certificateDto.getDescription());
            assertEquals(certificate.getPrice(), certificateDto.getPrice());
            assertEquals(certificate.getDuration(), certificateDto.getDuration());
        }

        verify(certificateDao).findByTagNames(tagNames);
        verifyNoMoreInteractions(certificateDao, orderMapper);
    }

    @Test
    @DisplayName("Save Order")
    void saveOrderTest() {
        when(orderDao.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.save(order);

        assertNotNull(result);
        assertEquals(orderDto.getId(), result.getId());
        assertEquals(orderDto.getOrderDate(), result.getOrderDate());
        assertEquals(orderDto.getCost(), result.getCost());
        assertEquals(orderDto.getUser().getId(), result.getUser().getId());
        assertEquals(orderDto.getUser().getUsername(), result.getUser().getUsername());
        assertEquals(orderDto.getUser().getEmail(), result.getUser().getEmail());

        verify(orderDao).save(order);
        verify(orderMapper).toDto(order);
        verifyNoMoreInteractions(orderDao, orderMapper);
    }
}
