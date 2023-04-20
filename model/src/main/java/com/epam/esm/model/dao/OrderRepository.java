package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long id);

    Certificate findCertificateById(Long id);

    @Query(value = "SELECT tag_id, COUNT(*) AS count FROM orders\n" +
            "JOIN order_gift_certificate ogc on orders.id = ogc.order_id\n" +
            "JOIN gift_certificate_tag gct on ogc.gift_certificate_id = gct.gift_certificate_id\n" +
            "WHERE user_id = :userId\n" +
            "GROUP BY tag_id\n" +
            "ORDER BY count DESC, SUM(ogc.price) DESC\n" +
            "LIMIT 1", nativeQuery = true)
    Optional<Tag> getMostUsedTags(@Param("userId") Long userId);
}
