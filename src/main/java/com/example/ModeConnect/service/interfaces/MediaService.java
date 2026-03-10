package com.example.ModeConnect.service.interfaces;

import com.example.ModeConnect.DTO.response.ModelMediaDto;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    ModelMediaDto uploadMedia(Long modelId, MultipartFile file);
    void deleteMedia(Long mediaId);
}

