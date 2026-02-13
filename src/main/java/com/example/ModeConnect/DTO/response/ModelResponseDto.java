package com.example.ModeConnect.DTO.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Data  // Add this annotation
@AllArgsConstructor
@NoArgsConstructor
public class ModelResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal purchasePrice;
    private BigDecimal rentalPrice;
    private Long creatorId;
    private String creatorName;
    private List<ModelMediaDto> mediaList;
}
