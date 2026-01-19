package com.example.ModeConnect.model;

import com.example.ModeConnect.Enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ModelMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;
}
