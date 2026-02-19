package com.example.ModeConnect.service.implementation;

import com.example.ModeConnect.DTO.request.OrderRequestDto;
import com.example.ModeConnect.DTO.response.OrderResponseDto;
import com.example.ModeConnect.Enums.OrderStatus;
import com.example.ModeConnect.Enums.OrderType;
import com.example.ModeConnect.Repository.ModelRepository;
import com.example.ModeConnect.Repository.OrderRepository;
import com.example.ModeConnect.Repository.UserRepository;
import com.example.ModeConnect.mapper.OrderMapper;
import com.example.ModeConnect.model.Model;
import com.example.ModeConnect.model.Order;
import com.example.ModeConnect.model.User;
import com.example.ModeConnect.service.implementation.mail.EmailNotificationService;
import com.example.ModeConnect.service.interfaces.OrderServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImp implements OrderServiceInterface {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;
    private final OrderMapper orderMapper;
    private  final EmailNotificationService emailNotificationService;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto dto,Long modelId) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));



        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("Model not found"));
        User creator = model.getCreator();

        if (dto.getOrderType() == OrderType.RENTAL) {
            if (dto.getReservation_days() == null || dto.getReservation_days() <= 0) {
                throw new RuntimeException("Reservation days are required for rental orders");
            }
        }

        Order order = orderMapper.toEntity(dto, client, model);
        order.setClient(client);
        order.setStatus(OrderStatus.PENDING);

        if(dto.getReservation_days()!=null){
            order.setReservation_days(dto.getReservation_days());
            order.setReservationDate(dto.getReservationDate());
        }
        Order savedOrder = orderRepository.save(order);
        emailNotificationService.sendEmailToCreator( creator.getEmail(),
                "Nouvelle commande reçue 📦",
                "Bonjour " + creator.getUsername() + ",\n\n" +
                        "Une nouvelle commande a été créée pour votre modèle : " +
                        model.getName() + ".\n\n" +
                        "Client : " + client.getEmail());
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getOrderCreator() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User creator = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

     List<Order> ordersCreatoreList= orderRepository.getOrdersByCreator(creator.getId());

        return orderMapper.toDtoList(ordersCreatoreList);
    }


}
