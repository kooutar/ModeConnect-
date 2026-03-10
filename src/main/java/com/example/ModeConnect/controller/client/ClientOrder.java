package com.example.ModeConnect.controller.client;

import com.example.ModeConnect.DTO.request.OrderRequestDto;
import com.example.ModeConnect.DTO.response.ModelResponseDto;
import com.example.ModeConnect.DTO.response.OrderResponseDto;
import com.example.ModeConnect.Repository.UserRepository;
import com.example.ModeConnect.service.interfaces.ModelInterface;
import com.example.ModeConnect.service.interfaces.OrderServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/order")
@RequiredArgsConstructor
public class ClientOrder {
    private final OrderServiceInterface orderService;
    private final UserRepository userRepository;
    private  final ModelInterface modelService;

    @PostMapping("/{modelId}")
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderRequestDto dto, @PathVariable Long modelId) {
        OrderResponseDto response = orderService.createOrder(dto,modelId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/rented")
    public ResponseEntity<List<ModelResponseDto>> getRentedModels() {
        return ResponseEntity.ok(modelService.findRentedByCurrentUser());
    }
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersForClient() {
        return ResponseEntity.ok(orderService.getOrdersByClient());
    }
}
