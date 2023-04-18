package com.epam.esm.api.controller;

import com.epam.esm.model.domain.Order;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/orders")
public class OrderController {
    private final OrderService orderService;
    private final CertificateService certificateService;
    private final UserService userService;

    @PostMapping("/orders")
    public OrderDto create(
            @PathVariable Long userId,
            @RequestParam Long certificateId) {
        return orderService.save(
                Order.builder()
                        .user(userService.findById(userId))
                        .certificate(certificateService
                                .findById(certificateId))
                        .build());
    }
}
