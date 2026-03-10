package com.example.ModeConnect.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private Long clientId;
    private Long orderId;
    private String comment;
    private Integer rate; // 1..5
}

