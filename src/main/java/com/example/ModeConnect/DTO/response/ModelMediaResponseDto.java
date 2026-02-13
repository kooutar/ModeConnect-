package com.example.ModeConnect.DTO.response;

import com.example.ModeConnect.Enums.MediaType;
import lombok.Data;

@Data
public class ModelMediaResponseDto {
    private String mediaUrl;
    private MediaType mediaType;
}