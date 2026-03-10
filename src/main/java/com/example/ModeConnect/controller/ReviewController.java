package com.example.ModeConnect.controller;

import com.example.ModeConnect.DTO.request.ReviewRequestDto;
import com.example.ModeConnect.DTO.response.ReviewResponseDto;
import com.example.ModeConnect.service.interfaces.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Poster un avis sur une commande
    @PostMapping("/client/reviews")
    public ResponseEntity<ReviewResponseDto> createReview( @RequestBody ReviewRequestDto dto) {
        ReviewResponseDto response = reviewService.createReview(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Récupérer les avis d'une commande
    @GetMapping("/orders/{orderId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByOrder(@PathVariable Long orderId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByOrder(orderId);
        return ResponseEntity.ok(reviews);
    }

    // Supprimer un avis (seul l'auteur peut supprimer)
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}

