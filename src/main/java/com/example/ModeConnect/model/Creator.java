package com.example.ModeConnect.model;

import com.example.ModeConnect.Enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ROLE_CREATOR")
public class Creator extends User{



    @Override
    public String getRole() {
        return Role.ROLE_CREATOR.name();
    }
}
