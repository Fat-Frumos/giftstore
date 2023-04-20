package com.epam.esm.service;

import com.epam.esm.model.entity.*;
import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto save(Order order);

    OrderDto createOrder(Long userId, List<Long> certificateIds);

    List<OrderDto> getUserOrders(User user, int page, int size);

    OrderDto getUserOrder(User user, Long orderId);

    Tag getMostUsedTags(User user);

    List<CertificateDto> getCertificatesByTags(Criteria criteria);

    List<Order> findByUserId(Long id);

    Certificate findCertificateById(Long id);
}
