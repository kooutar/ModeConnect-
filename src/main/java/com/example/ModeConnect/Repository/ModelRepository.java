package com.example.ModeConnect.Repository;

import com.example.ModeConnect.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model,Long> {

    // exemples utiles (optionnels)
    List<Model> findByCreatorId(Long creatorId);

    List<Model> findByNameContainingIgnoreCase(String name);

}
