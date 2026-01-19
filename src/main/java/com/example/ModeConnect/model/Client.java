package com.example.ModeConnect.model;

import com.example.ModeConnect.Enums.Role;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ROLE_CLIENT")
public class Client extends User{
    @Override
    public String getRole() {
        return Role.ROLE_CLIENT.name();
    }

}
