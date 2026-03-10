package com.example.ModeConnect.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private Long clientId;
    private String clientName;
    private Long orderId;
    private String comment;
    private Integer rate;
    private LocalDateTime createdAt;
}

