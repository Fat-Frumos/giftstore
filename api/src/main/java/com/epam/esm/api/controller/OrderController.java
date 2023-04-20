package com.epam.esm.api.controller;

import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    private final UserService userService;

    @PostMapping()
    public OrderDto create(
            @PathVariable Long userId,
            @RequestParam Long certificateId) {
        Certificate certificate = orderService.findCertificateById(certificateId);
        Order order = Order.builder()
                .certificates(new HashSet<>(Collections.singleton(certificate)))
                .amount(certificate.getPrice())
                .user(userService.findById(userId))
                .orderDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return orderService.save(order);
    }
    @GetMapping()
    public List<OrderDto> getOrdersForUser(@PathVariable Long userId) {
        List<Order> orders = orderService.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(toList());
    }
}
