package com.example.ModeConnect.service.implementation;

import com.example.ModeConnect.DTO.request.ModelMediaRequestDto;
import com.example.ModeConnect.DTO.request.ModelRequestDto;
import com.example.ModeConnect.DTO.response.ModelResponseDto;
import com.example.ModeConnect.Repository.ModelRepository;
import com.example.ModeConnect.Repository.UserRepository;
import com.example.ModeConnect.mapper.MapperModel;
import com.example.ModeConnect.model.Model;
import com.example.ModeConnect.model.ModelMedia;
import com.example.ModeConnect.model.User;
import com.example.ModeConnect.service.interfaces.ModelInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ModelImp implements ModelInterface {

    private final ModelRepository modelRepository;
    private final MapperModel modelMapper;
    private final UserRepository userRepository;

    // ================= CREATE =================
    @Override
    public ModelResponseDto create(ModelRequestDto dto) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User creator = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Model model = modelMapper.toEntity(dto);
        model.setCreator(creator);

        // lier chaque media au model (IMPORTANT)
        // 🔥 FIX MEDIA RELATION
        if (dto.getMediaList() != null) {
            List<ModelMedia> mediaList = new ArrayList<>();

            for (ModelMediaRequestDto mediaDto : dto.getMediaList()) {
                ModelMedia media = new ModelMedia();
                media.setMediaUrl(mediaDto.getMediaUrl());
                media.setMediaType(mediaDto.getMediaType()); // ⚠️ ICI
                media.setModel(model);                       // FK

                mediaList.add(media);
            }

            model.setMediaList(mediaList);
        }

        Model saved = modelRepository.save(model);
        return modelMapper.toDto(saved);
    }

    // ================= READ ALL =================
    @Override
    public List<ModelResponseDto> findAll() {
        return modelMapper.toDtoList(modelRepository.findAll());
    }

    // ================= READ BY ID =================
    @Override
    public ModelResponseDto findById(Long id) {
        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found"));

        return modelMapper.toDto(model);
    }

    // ================= UPDATE =================
    @Override
    public ModelResponseDto update(Long id, ModelRequestDto dto) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found"));

        // sécurité : seul le creator peut modifier
        if (!model.getCreator().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not allowed to update this model");
        }

        // update champs simples
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setPurchasePrice(dto.getPurchasePrice());
        model.setRentalPrice(dto.getRentalPrice());

        // update media (orphanRemoval = true fait le ménage)
        model.getMediaList().clear();

//        if (dto.getMediaList() != null) {
//            dto.getMediaList().forEach(mediaDto -> {
//                ModelMedia media = modelMapper.toMediaEntity(mediaDto);
//                media.setModel(model);
//                model.getMediaList().add(media);
//            });
//        }

        Model updated = modelRepository.save(model);
        return modelMapper.toDto(updated);
    }

    // ================= DELETE =================
    @Override
    public void delete(Long id) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found"));

        if (!model.getCreator().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not allowed to delete this model");
        }

        modelRepository.delete(model);
    }
}

