package com.epam.esm.service;

import com.epam.esm.model.domain.Criteria;
import com.epam.esm.model.domain.Order;
import com.epam.esm.model.domain.Tag;
import com.epam.esm.model.domain.User;
import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto save(Order order);
    List<OrderDto> getUserOrders(User user, int page, int size);
    OrderDto getUserOrder(User user, Long orderId);
    Tag getMostUsedTags(User user);
    List<CertificateDto> getCertificatesByTags(Criteria criteria);
}
