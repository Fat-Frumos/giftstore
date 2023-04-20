package com.epam.esm.service;

import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.dao.CertificateRepository;
import com.epam.esm.model.dao.OrderRepository;
import com.epam.esm.model.dao.UserRepository;
import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;

    @Override
    public OrderDto save(Order order) {
        return mapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDto createOrder(Long userId, List<Long> certificateIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with id %d not found", userId)));

        Set<Certificate> certificates = StreamSupport.stream(
                certificateRepository.findAllById(certificateIds).spliterator(), false)
                .collect(toSet());

        if (certificates.size() != certificateIds.size()) {
            throw new CertificateNotFoundException("One or more certificates not found");
        }

        BigDecimal cost = certificates.stream()
                .map(Certificate::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .user(user)
                .certificates(certificates)
                .orderDate(Timestamp.valueOf(LocalDateTime.now()))
                .amount(cost)
                .build();

        return mapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getUserOrders(User user, int page, int size) {
        return null;
    }

    @Override
    public OrderDto getUserOrder(User user, Long orderId) {
        return null;
    }

    @Override
    public Tag getMostUsedTags(User user) {
        return orderRepository.getMostUsedTags(user.getId()).orElseThrow(()->
                new TagNotFoundException("Tag Not found Exception"));
    }

    @Override
    public List<CertificateDto> getCertificatesByTags(Criteria criteria) {
        return null;
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Certificate findCertificateById(Long id) {
        return orderRepository.findCertificateById(id);
    }
}
