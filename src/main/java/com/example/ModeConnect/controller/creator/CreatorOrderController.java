package com.example.ModeConnect.controller.creator;

import com.example.ModeConnect.DTO.response.OrderResponseDto;
import com.example.ModeConnect.service.interfaces.OrderServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/creator/order")
public class CreatorOrderController {
    final OrderServiceInterface orderService;
    @GetMapping
    public List<OrderResponseDto> getAllOrder(){
        return  orderService.getOrderCreator();
    }
    @PutMapping("/{orderId}/accept")
    public OrderResponseDto acceptOrder(@PathVariable Long orderId) {
        return orderService.acceptOrder(orderId);
    }

    @PutMapping("/{orderId}/reject")
    public OrderResponseDto rejectOrder(@PathVariable Long orderId) {
        return orderService.rejectOrder(orderId);
    }
}
