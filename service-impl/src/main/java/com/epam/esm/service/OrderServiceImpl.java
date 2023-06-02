package com.epam.esm.service;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the OrderService interface.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    /**
     * The data access object for User entities.
     */
    private final UserDao userDao;
    /**
     * The data access object for Order entities.
     */
    private final OrderDao orderDao;
    /**
     * The data access object for Certificate entities.
     */
    private final CertificateDao certificateDao;
    /**
     * The mapper for converting Order entities to DTOs and vice versa.
     */
    private final OrderMapper orderMapper;

    /**
     * {@inheritDoc}
     * <p>
     * Saves an order.
     *
     * @param userId the user ID
     * @return the saved order DTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public OrderDto save(final Long userId, Set<Long> ids) {
        User user = userDao.getById(userId).orElseThrow(() ->
                new UserNotFoundException("UserNotFoundException"));
        List<Certificate> certificates =
                certificateDao.findAllByIds(ids);
        Order order = Order.builder()
                .certificates(new HashSet<>(certificates))
                .cost(certificates.stream()
                        .map(Certificate::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .user(user)
                .orderDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return orderMapper.toDto(
                orderDao.save(order));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return the order DTO
     * @throws OrderNotFoundException if the order is not found
     */
    @Override
    @Transactional(readOnly = true)
    public OrderDto getById(final Long id) {
        Order order = orderDao.getById(id).orElseThrow(() ->
                new OrderNotFoundException(
                        String.format("Order with id %d not found", id)));
        return orderMapper.toDto(order);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates an order for a user with the specified certificate IDs.
     *
     * @param userId         the ID of the user
     * @param certificateIds the IDs of the certificates
     * @return the created order DTO
     * @throws UserNotFoundException        if the user is not found
     * @throws CertificateNotFoundException if one or more certificates are not found
     */
    @Override
    @Transactional(readOnly = true)
    public OrderDto createOrder(Long userId, Set<Long> certificateIds) {
        User user = userDao.getById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with id %d not found", userId)));
        List<Certificate> certificates = certificateDao
                .findAllByIds(certificateIds);
        if (certificates.size() != certificateIds.size()) {
            throw new CertificateNotFoundException(
                    "One or more certificates not found");
        }
        Order order = Order.builder()
                .user(user)
                .certificates(new HashSet<>(certificates))
                .orderDate(Timestamp.valueOf(LocalDateTime.now()))
                .cost(certificates.stream()
                        .map(Certificate::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();

        return orderMapper.toDto(orderDao.save(order));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a page of orders for a specific user.
     *
     * @param user     the user
     * @param pageable the pageable object for pagination
     * @return a page of order DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> getUserOrders(
            final User user,
            final Pageable pageable) {
        List<OrderDto> dtos = orderMapper.toDtoList(
                orderDao.getUserOrders(user, pageable));
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a page of all orders.
     *
     * @param pageable the pageable object for pagination
     * @return a page of order DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> getAll(
            final Pageable pageable) {
        List<OrderDto> dtos = orderMapper.toDtoList(
                orderDao.getAllBy(pageable));
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a specific order for a user.
     *
     * @param userId  the ID of the user
     * @param orderId the ID of the order
     * @return the order DTO
     * @throws OrderNotFoundException if the order is not found
     */
    @Override
    @Transactional(readOnly = true)
    public OrderDto getUserOrder(
            final Long userId,
            final Long orderId) {
        Order order = orderDao.getUserOrder(userId, orderId)
                .orElseThrow(() -> new OrderNotFoundException(
                        String.format("Order not found with id %d", orderId)));
        return orderMapper.toDto(order);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves the most used tag by a user.
     *
     * @param userId the ID of the user
     * @return the most used tag
     * @throws TagNotFoundException if the tag is not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Tag> getMostUsedTags(
            final Long userId) {
        return orderDao.getMostUsedTagBy(userId);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves all orders for a specific user.
     *
     * @param userId the ID of the user
     * @return a page of order DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> getAllByUserId(
            final Long userId) {
        List<OrderDto> dtos = orderMapper.toDtoList(
                orderDao.findOrdersByUserId(userId));
        return new PageImpl<>(dtos, Pageable.unpaged(), dtos.size());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a certificate by its ID.
     *
     * @param ids the ID of the certificate
     * @return the certificate
     * @throws CertificateNotFoundException if the certificate is not found
     */
    @Override
    @Transactional(readOnly = true)
    public List<Certificate> findCertificateById(
            final Set<Long> ids) {
        return certificateDao.findAllByIds(ids);
    }
}
