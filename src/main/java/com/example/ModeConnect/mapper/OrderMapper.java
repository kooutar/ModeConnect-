package com.example.ModeConnect.mapper;


import com.example.ModeConnect.DTO.request.OrderRequestDto;
import com.example.ModeConnect.DTO.response.OrderResponseDto;
import com.example.ModeConnect.model.Model;
import com.example.ModeConnect.model.Order;
import com.example.ModeConnect.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true) // Ignorer l'id de la base
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "client", source = "client")  // l'objet User
    @Mapping(target = "model", source = "model")    // l'objet Model
    @Mapping(target = "orderType", source = "dto.orderType") // si orderType vient du DTO
    Order toEntity(OrderRequestDto dto, User client, Model model);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.username")
    @Mapping(target = "modelId", source = "model.id")
    @Mapping(target = "modelName", source = "model.name")
    OrderResponseDto toDto(Order order);

    List<OrderResponseDto> toDtoList(List<Order> orders);
}
