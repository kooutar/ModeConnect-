package com.example.ModeConnect.service.interfaces;

import com.example.ModeConnect.DTO.request.ReviewRequestDto;
import com.example.ModeConnect.DTO.response.ReviewResponseDto;

import java.util.List;

public interface ReviewService {
    ReviewResponseDto createReview(ReviewRequestDto dto);
    List<ReviewResponseDto> getReviewsByOrder(Long orderId);
    void deleteReview(Long reviewId);
}

