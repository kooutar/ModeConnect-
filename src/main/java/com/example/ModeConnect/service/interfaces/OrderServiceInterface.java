package com.example.ModeConnect.service.interfaces;

import com.example.ModeConnect.DTO.request.OrderRequestDto;
import com.example.ModeConnect.DTO.response.OrderResponseDto;
import com.example.ModeConnect.mapper.OrderMapper;
import com.example.ModeConnect.model.User;

import java.util.List;

public interface OrderServiceInterface {
    public OrderResponseDto createOrder(
            OrderRequestDto dto,Long modelId);

    public List<OrderResponseDto> getOrderCreator();
    OrderResponseDto acceptOrder(Long orderId);

    OrderResponseDto rejectOrder(Long orderId);
}
