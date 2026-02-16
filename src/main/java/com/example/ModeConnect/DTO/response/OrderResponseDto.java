package com.example.ModeConnect.DTO.response;

import com.example.ModeConnect.Enums.OrderStatus;
import com.example.ModeConnect.Enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;
    private Long clientId;
    private String clientName; // ou email
    private Long modelId;
    private String modelName;
    private OrderType orderType;
    private LocalDateTime createdAt;
    private Integer reservation_days;
    private LocalDate reservationDate;
    private OrderStatus status;


}
