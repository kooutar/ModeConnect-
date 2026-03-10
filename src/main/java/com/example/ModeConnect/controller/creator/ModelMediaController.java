package com.example.ModeConnect.controller.creator;

import com.example.ModeConnect.DTO.response.ModelMediaDto;
import com.example.ModeConnect.service.interfaces.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/creator/models")
@RequiredArgsConstructor
public class ModelMediaController {

    private final MediaService mediaService;

    @PostMapping("/{modelId}/media")
    public ResponseEntity<ModelMediaDto> uploadMedia(@PathVariable Long modelId,
                                                    @RequestParam("file") MultipartFile file) {
        ModelMediaDto dto = mediaService.uploadMedia(modelId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/media/{mediaId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long mediaId) {
        mediaService.deleteMedia(mediaId);
        return ResponseEntity.noContent().build();
    }
}

