package com.example.ModeConnect.Repository;

import com.example.ModeConnect.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOrderId(Long orderId);
    List<Review> findByClientId(Long clientId);
}

