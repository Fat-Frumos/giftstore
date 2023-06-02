package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service interface for managing orders.
 */
public interface OrderService {

    /**
     * Get a specific order of a user.
     *
     * @param userId    the user
     * @param orderId the order ID
     * @return the order DTO
     */
    OrderDto getUserOrder(Long userId, Long orderId);

    /**
     * Get the most used tags by a user.
     *
     * @param userId the user ID
     * @return the most used tag
     */
    Optional<Tag> getMostUsedTags(Long userId);

    /**
     * Get all orders of a user.
     *
     * @param userId the user ID
     * @return the page of order DTOs
     */
    Page<OrderDto> getAllByUserId(Long userId);

    /**
     * Create an order for a user with the specified certificate IDs.
     *
     * @param userId         the user ID
     * @param certificateIds the set of certificate IDs
     * @return the created order DTO
     */
    OrderDto createOrder(Long userId, Set<Long> certificateIds);

    /**
     * Get paginated user orders.
     *
     * @param user     the user
     * @param pageable the pageable information
     * @return the page of order DTOs
     */
    Page<OrderDto> getUserOrders(User user, Pageable pageable);

    /**
     * Get all orders with pagination.
     *
     * @param pageable the pageable information
     * @return the page of order DTOs
     */
    Page<OrderDto> getAll(Pageable pageable);

    /**
     * Find a certificate by ID.
     *
     * @param ids the certificate IDs
     * @return the certificate
     */
    List<Certificate> findCertificateById(Set<Long> ids);

    /**
     * Save an order.
     *
     * @param userId the user IS
     * @param ids the certificate IDs
     * @return the saved order DTO
     */
    OrderDto save(Long userId, Set<Long> ids);

    /**
     * Get an order by ID.
     *
     * @param id the order ID
     * @return the order DTO
     */
    OrderDto getById(Long id);
}
