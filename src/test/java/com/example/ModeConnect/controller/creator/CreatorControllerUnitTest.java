package com.example.ModeConnect.controller.creator;

import com.example.ModeConnect.DTO.request.ModelRequestDto;
import com.example.ModeConnect.DTO.response.ModelResponseDto;
import com.example.ModeConnect.service.interfaces.ModelInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreatorControllerUnitTest {

    @Test
    void createModel() {
        ModelInterface modelService = Mockito.mock(ModelInterface.class);
        CreatorController controller = new CreatorController(modelService);

        ModelRequestDto req = new ModelRequestDto();
        req.setName("New");
        req.setDescription("d");
        req.setPurchasePrice(new BigDecimal("20.00"));
        req.setRentalPrice(new BigDecimal("2.00"));

        ModelResponseDto resp = new ModelResponseDto();
        resp.setId(10L);
        resp.setName("New");

        when(modelService.create(any())).thenReturn(resp);

        ResponseEntity<ModelResponseDto> response = controller.create(req);
        assertEquals(10L, response.getBody().getId());
        assertEquals("New", response.getBody().getName());
    }

    @Test
    void findAll() {
        ModelInterface modelService = Mockito.mock(ModelInterface.class);
        CreatorController controller = new CreatorController(modelService);

        ModelResponseDto dto = new ModelResponseDto();
        dto.setId(3L);
        dto.setName("X");

        when(modelService.findAll()).thenReturn(List.of(dto));

        ResponseEntity<java.util.List<ModelResponseDto>> response = controller.findAll();
        assertEquals(1, response.getBody().size());
        assertEquals("X", response.getBody().get(0).getName());
    }
}

