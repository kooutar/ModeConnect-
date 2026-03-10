package com.example.ModeConnect.service.implementation;

import com.example.ModeConnect.DTO.request.ReviewRequestDto;
import com.example.ModeConnect.DTO.response.ReviewResponseDto;
import com.example.ModeConnect.Repository.OrderRepository;
import com.example.ModeConnect.Repository.ReviewRepository;
import com.example.ModeConnect.Repository.UserRepository;
import com.example.ModeConnect.mapper.ReviewMapper;
import com.example.ModeConnect.model.Order;
import com.example.ModeConnect.model.Review;
import com.example.ModeConnect.model.User;
import com.example.ModeConnect.service.interfaces.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImp implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDto createReview(ReviewRequestDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Vérifier que la commande appartient au client courant
        if (!order.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("You can only review your own orders");
        }

        // Mapper et forcer le client et l'ordre à l'entité
        Review review = reviewMapper.toEntity(dto);
        review.setClient(client);
        review.setOrder(order);

        // Sauvegarder
        Review saved = reviewRepository.save(review);
        return reviewMapper.toDto(saved);
    }

    @Override
    public List<ReviewResponseDto> getReviewsByOrder(Long orderId) {
        List<Review> reviews = reviewRepository.findByOrderId(orderId);
        return reviews.stream().map(reviewMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("You can only delete your own reviews");
        }

        reviewRepository.delete(review);
    }
}

