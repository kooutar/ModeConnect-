package com.example.ModeConnect.DTO.request;

import com.example.ModeConnect.Enums.OrderType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class OrderRequestDto {
    private Long id;
    private Long clientId;
    private String clientName; // ou email
    private Long modelId;
    private String modelName;
    private OrderType orderType;
    private LocalDateTime createdAt;
    private Integer reservation_days;
    private LocalDate reservationDate;
}
