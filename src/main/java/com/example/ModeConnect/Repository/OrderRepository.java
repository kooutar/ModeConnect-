package com.example.ModeConnect.Repository;

import com.example.ModeConnect.DTO.response.OrderResponseDto;
import com.example.ModeConnect.Enums.OrderType;
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

    // Vérifie si un client a déjà passé une commande pour un même modèle
    boolean existsByClientIdAndModelId(Long clientId, Long modelId);

    // Récupérer toutes les commandes d'un client pour un type d'ordre (ex: RENTAL)
    List<Order> findByClientIdAndOrderType(Long clientId, OrderType orderType);

    // Récupérer toutes les commandes d'un client
    List<Order> findByClientId(Long clientId);
}
