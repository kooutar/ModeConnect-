package com.example.ModeConnect.service;

import com.example.ModeConnect.DTO.request.ModelMediaRequestDto;
import com.example.ModeConnect.DTO.request.ModelRequestDto;
import com.example.ModeConnect.DTO.response.ModelResponseDto;
import com.example.ModeConnect.Enums.MediaType;
import com.example.ModeConnect.Enums.OrderType;
import com.example.ModeConnect.Repository.ModelRepository;
import com.example.ModeConnect.Repository.OrderRepository;
import com.example.ModeConnect.Repository.ReviewRepository;
import com.example.ModeConnect.Repository.UserRepository;
import com.example.ModeConnect.mapper.MapperModel;
import com.example.ModeConnect.mapper.ReviewMapper;
import com.example.ModeConnect.model.Client;
import com.example.ModeConnect.model.Model;
import com.example.ModeConnect.model.ModelMedia;
import com.example.ModeConnect.service.implementation.ModelImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ModelImpTest {

    private ModelRepository modelRepository;
    private MapperModel modelMapper;
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private ReviewRepository reviewRepository;
    private ReviewMapper reviewMapper;
    private ModelImp modelService;

    @BeforeEach
    void setUp() {
        modelRepository = Mockito.mock(ModelRepository.class);
        modelMapper = Mockito.mock(MapperModel.class);
        userRepository = Mockito.mock(UserRepository.class);
        orderRepository = Mockito.mock(OrderRepository.class);
        reviewRepository = Mockito.mock(ReviewRepository.class);
        reviewMapper = Mockito.mock(ReviewMapper.class);

        modelService = new ModelImp(modelRepository, modelMapper, userRepository, orderRepository, reviewRepository, reviewMapper);
    }

    private void mockSecurityUserEmail(String email) {
        Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext ctx = Mockito.mock(SecurityContext.class);
        when(auth.getName()).thenReturn(email);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);
    }

    @Test
    void createModel_withMedia_linksMediaAndSaves() {
        mockSecurityUserEmail("creator@test.com");

        Client creator = new Client();
        creator.setId(5L);
        creator.setEmail("creator@test.com");

        when(userRepository.findByEmail("creator@test.com")).thenReturn(Optional.of(creator));

        ModelRequestDto dto = new ModelRequestDto();
        dto.setName("M");
        dto.setDescription("d");
        ModelMediaRequestDto mediaDto = new ModelMediaRequestDto();
        mediaDto.setMediaUrl("/uploads/1.png");
        mediaDto.setMediaType(MediaType.IMAGE);
        dto.setMediaList(List.of(mediaDto));

        Model modelEntity = new Model();
        when(modelMapper.toEntity(dto)).thenReturn(modelEntity);

        Model saved = new Model();
        saved.setId(7L);
        when(modelRepository.save(Mockito.any(Model.class))).thenReturn(saved);
        when(modelMapper.toDto(saved)).thenReturn(new ModelResponseDto());

        ModelResponseDto resp = modelService.create(dto);
        assertNotNull(resp);
        // verify media linked
        ArgumentCaptor<Model> captor = ArgumentCaptor.forClass(Model.class);
        Mockito.verify(modelRepository).save(captor.capture());
        Model passed = captor.getValue();
        assertEquals(1, passed.getMediaList().size());
        ModelMedia media = passed.getMediaList().get(0);
        assertEquals("/uploads/1.png", media.getMediaUrl());
        assertEquals(passed, media.getModel());
    }

    @Test
    void findAll_enrichesReviews() {
        Model m = new Model();
        m.setId(2L);
        when(modelRepository.findAll()).thenReturn(List.of(m));

        when(modelMapper.toDtoList(List.of(m))).thenReturn(List.of(new ModelResponseDto()));
        when(reviewRepository.findByModelId(2L)).thenReturn(List.of());
        when(reviewMapper.toDtoList(List.of())).thenReturn(List.of());

        List<ModelResponseDto> list = modelService.findAll();
        assertEquals(1, list.size());
    }

    @Test
    void findRentedByCurrentUser_returnsModelsFromOrders() {
        mockSecurityUserEmail("client@test.com");
        Client client = new Client();
        client.setId(9L);
        client.setEmail("client@test.com");

        when(userRepository.findByEmail("client@test.com")).thenReturn(Optional.of(client));

        // create an order mock
        com.example.ModeConnect.model.Order o = new com.example.ModeConnect.model.Order();
        Model m = new Model();
        m.setId(11L);
        o.setModel(m);
        when(orderRepository.findByClientIdAndOrderType(9L, OrderType.RENTAL)).thenReturn(List.of(o));

        when(modelMapper.toDtoList(List.of(m))).thenReturn(List.of(new ModelResponseDto()));

        List<ModelResponseDto> res = modelService.findRentedByCurrentUser();
        assertEquals(1, res.size());
    }
}
