package com.example.ModeConnect.Repository;

import com.example.ModeConnect.DTO.response.OrderResponseDto;
import com.example.ModeConnect.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("""
    SELECT o
    FROM Order o
    JOIN o.model m
    WHERE m.creator.id = :creatorId
""")
 public List<Order> getOrdersByCreator(@Param("creatorId") Long creatorId);
}
