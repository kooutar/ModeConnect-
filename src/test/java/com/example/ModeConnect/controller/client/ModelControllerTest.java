package com.example.ModeConnect.controller.client;

import com.example.ModeConnect.DTO.response.ModelResponseDto;
import com.example.ModeConnect.service.interfaces.ModelInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ModelControllerTest {

    @Test
    void getAllModels() {
        ModelInterface modelService = Mockito.mock(ModelInterface.class);
        ModelController controller = new ModelController(modelService);

        ModelResponseDto dto = new ModelResponseDto();
        dto.setId(1L);
        dto.setName("M1");
        dto.setDescription("desc");
        dto.setPurchasePrice(new BigDecimal("10.00"));
        dto.setRentalPrice(new BigDecimal("1.00"));

        when(modelService.findAll()).thenReturn(List.of(dto));

        ResponseEntity<List<ModelResponseDto>> response = controller.getAllModels();
        assertEquals(1, response.getBody().size());
        assertEquals("M1", response.getBody().get(0).getName());
    }

    @Test
    void getModelById() {
        ModelInterface modelService = Mockito.mock(ModelInterface.class);
        ModelController controller = new ModelController(modelService);

        ModelResponseDto dto = new ModelResponseDto();
        dto.setId(2L);
        dto.setName("M2");

        when(modelService.findById(2L)).thenReturn(dto);

        ResponseEntity<ModelResponseDto> response = controller.getModelById(2L);
        assertEquals(2L, response.getBody().getId());
        assertEquals("M2", response.getBody().getName());
    }
}
