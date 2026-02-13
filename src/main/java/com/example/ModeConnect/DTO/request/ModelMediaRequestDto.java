package com.example.ModeConnect.DTO.request;

import com.example.ModeConnect.Enums.MediaType;
import lombok.Data;

@Data
public class ModelMediaRequestDto {
    private String mediaUrl;
    private MediaType mediaType; // ⚠️ PAS String
}
