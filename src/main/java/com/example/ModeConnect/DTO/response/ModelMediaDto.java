package com.example.ModeConnect.DTO.response;

import com.example.ModeConnect.Enums.MediaType;
import lombok.Data;

@Data
public class ModelMediaDto {
    private String mediaUrl;
    private MediaType mediaType; // ⚠️ PAS String
}
