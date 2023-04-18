package com.epam.esm.service;

import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.dao.OrderRepository;
import com.epam.esm.model.domain.Criteria;
import com.epam.esm.model.domain.Order;
import com.epam.esm.model.domain.Tag;
import com.epam.esm.model.domain.User;
import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public OrderDto save(Order order) {
        return mapper.toDto(
                repository.save(order));
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
        return null;
    }

    @Override
    public List<CertificateDto> getCertificatesByTags(Criteria criteria) {
        return null;
    }
}
