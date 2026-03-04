package com.arshad.smart_pos_system.mapper;

import com.arshad.smart_pos_system.dto.StoreDto;
import com.arshad.smart_pos_system.model.Store;
import com.arshad.smart_pos_system.model.StoreContact;
import com.arshad.smart_pos_system.model.User;

import java.time.LocalDateTime;

public class StoreMapper {

    // ============================
    // ENTITY → DTO
    // ============================
    public static StoreDto toDto(Store store) {

        if (store == null) {
            return null;
        }

        StoreDto dto = new StoreDto();

        dto.setId(store.getId());
        dto.setBrand(store.getBrand());
        dto.setDescription(store.getDescription());
        dto.setStoreType(store.getStoreType());
        dto.setStatus(store.getStatus());
        dto.setCreatedAt(store.getCreatedAt());
        dto.setUpdatedAt(store.getUpdatedAt());

        // Safe admin mapping
        if (store.getStoreAdmin() != null) {
            dto.setStoreAdmin(UserMapper.toDto(store.getStoreAdmin()));
        }

        // Safe contact mapping
        if (store.getContact() != null) {
            dto.setContact(store.getContact());
        }

        return dto;
    }

    // ============================
    // DTO → ENTITY
    // ============================
    public static Store toEntity(StoreDto dto, User storeAdmin) {

        if (dto == null) {
            return null;
        }

        Store store = new Store();

        // ❌ DO NOT set ID here (DB handles it)

        store.setBrand(dto.getBrand());
        store.setDescription(dto.getDescription());
        store.setStoreType(dto.getStoreType());
        store.setStoreAdmin(storeAdmin);

        // Safe contact copy
        if (dto.getContact() != null) {
            StoreContact contact = StoreContact.builder()
                    .address(dto.getContact().getAddress())
                    .phone(dto.getContact().getPhone())
                    .email(dto.getContact().getEmail())
                    .build();

            store.setContact(contact);
        }

        // Set timestamps here (better handled in service)
        store.setCreatedAt(LocalDateTime.now());
        store.setUpdatedAt(LocalDateTime.now());

        return store;
    }
}
