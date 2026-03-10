package com.example.ModeConnect.controller.client;

import com.example.ModeConnect.DTO.response.ModelResponseDto;
import com.example.ModeConnect.service.interfaces.ModelInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelInterface modelService;

    // GET all models (public)
    @GetMapping
    public ResponseEntity<List<ModelResponseDto>> getAllModels() {
        return ResponseEntity.ok(modelService.findAll());
    }

    // GET model by id (public)
    @GetMapping("/{id}")
    public ResponseEntity<ModelResponseDto> getModelById(@PathVariable Long id) {
        return ResponseEntity.ok(modelService.findById(id));
    }

    // GET models rented by the current authenticated user

}
