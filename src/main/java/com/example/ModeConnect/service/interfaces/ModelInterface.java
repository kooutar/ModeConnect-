package com.example.ModeConnect.service.interfaces;

import com.example.ModeConnect.DTO.request.ModelRequestDto;
import com.example.ModeConnect.DTO.response.ModelResponseDto;

import java.util.List;

public interface ModelInterface {
    ModelResponseDto create(ModelRequestDto dto);

    List<ModelResponseDto> findAll();

    ModelResponseDto findById(Long id);

    ModelResponseDto update(Long id, ModelRequestDto dto);

    void delete(Long id);

}
