package com.arshad.smart_pos_system.dto;

import com.arshad.smart_pos_system.domain.StoreStatus;
import com.arshad.smart_pos_system.model.StoreContact;
import com.arshad.smart_pos_system.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDto
{

    private Long id;

    private String brand;

    private UserDto storeAdmin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description;

    private String storeType;

    private StoreStatus status;

    private StoreContact contact;

}
