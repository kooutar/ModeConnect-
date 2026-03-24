package com.example.ModeConnect.mapper;

import com.example.ModeConnect.DTO.request.ReviewRequestDto;
import com.example.ModeConnect.DTO.response.ReviewResponseDto;
import com.example.ModeConnect.model.Order;
import com.example.ModeConnect.model.Review;
import com.example.ModeConnect.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "clientId", qualifiedByName = "idToUser")
    @Mapping(target = "order", source = "orderId", qualifiedByName = "idToOrder")
    Review toEntity(ReviewRequestDto dto);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.username")
    @Mapping(target = "orderId", source = "order.id")
    ReviewResponseDto toDto(Review review);

    List<ReviewResponseDto> toDtoList(List<Review> reviews);

    @Named("idToUser")
    default User idToUser(Long id) {
        if (id == null) return null;
        User user = new User() {
            @Override
            public String getRole() {
                return null;
            }
        };
        user.setId(id);
        return user;
    }

    @Named("idToOrder")
    default Order idToOrder(Long id) {
        if (id == null) return null;
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
