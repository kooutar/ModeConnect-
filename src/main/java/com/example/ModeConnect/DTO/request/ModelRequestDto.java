package com.example.ModeConnect.DTO.request;

import com.example.ModeConnect.DTO.response.ModelMediaDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelRequestDto {
    private String name;
    private String description;
    private BigDecimal purchasePrice;
    private BigDecimal rentalPrice;
    private Long creatorId;
    private Boolean available;
    private List<ModelMediaRequestDto> mediaList;
}
