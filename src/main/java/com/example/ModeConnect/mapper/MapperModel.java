package com.example.ModeConnect.mapper;

import com.example.ModeConnect.DTO.request.ModelRequestDto;
import com.example.ModeConnect.DTO.response.ModelMediaDto;
import com.example.ModeConnect.DTO.response.ModelResponseDto;
import com.example.ModeConnect.Repository.UserRepository;
import com.example.ModeConnect.model.Creator;
import com.example.ModeConnect.model.Model;
import com.example.ModeConnect.model.ModelMedia;
import com.example.ModeConnect.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.*;

import java.util.List;
@Mapper(componentModel = "spring")
public interface MapperModel {
    // Entity → Response DTO
    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "creator.username", target = "creatorName")
    ModelResponseDto toDto(Model model);

    List<ModelResponseDto> toDtoList(List<Model> models);

    // Request DTO → Entity
    // Request DTO → Entity
    @Mapping(source = "creatorId", target = "creator", qualifiedByName = "idToUser")
    Model toEntity(ModelRequestDto dto);


    // Add this method to map ModelMedia to ModelMediaDto
    ModelMediaDto toMediaDto(ModelMedia modelMedia);

    // This will automatically handle List<ModelMedia> → List<ModelMediaDto>
    List<ModelMediaDto> toMediaDtoList(List<ModelMedia> mediaList);

    @Named("idToUser")
    default User idToUser(Long id) {
        if (id == null) return null;

        Creator creator = new Creator();
        creator.setId(id);
        return creator;
    }
}
