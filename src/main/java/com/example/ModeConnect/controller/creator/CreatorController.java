package com.example.ModeConnect.controller.creator;

import com.example.ModeConnect.DTO.request.ModelRequestDto;
import com.example.ModeConnect.DTO.response.ModelResponseDto;
import com.example.ModeConnect.service.interfaces.ModelInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/creator/models")
@RequiredArgsConstructor
public class CreatorController {

    private final ModelInterface modelService;

    // ================= CREATE =================
    @PostMapping("/create")
    public ResponseEntity<ModelResponseDto> create(
            @RequestBody ModelRequestDto dto
    ) {
        ModelResponseDto created = modelService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ================= READ ALL =================
    @GetMapping
    public ResponseEntity<List<ModelResponseDto>> findAll() {
        return ResponseEntity.ok(modelService.findAll());
    }

    // ================= READ BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<ModelResponseDto> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(modelService.findById(id));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<ModelResponseDto> update(
            @PathVariable Long id,
            @RequestBody ModelRequestDto dto
    ) {
        ModelResponseDto updated = modelService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        modelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

