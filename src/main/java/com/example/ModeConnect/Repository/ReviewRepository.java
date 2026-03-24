package com.example.ModeConnect.Repository;

import com.example.ModeConnect.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOrderId(Long orderId);
    List<Review> findByClientId(Long clientId);

    @Query("""
    SELECT r
    FROM Review r
    JOIN r.order o
    JOIN o.model m
    WHERE m.id = :modelId
""")
    List<Review> findByModelId(@Param("modelId") Long modelId);
}
