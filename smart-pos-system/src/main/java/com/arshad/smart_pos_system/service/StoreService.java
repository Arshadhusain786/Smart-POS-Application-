package com.arshad.smart_pos_system.service;

import com.arshad.smart_pos_system.domain.StoreStatus;
import com.arshad.smart_pos_system.dto.StoreDto;
import com.arshad.smart_pos_system.model.Store;

import java.util.List;

public interface StoreService {

    // Create store (ADMIN)
    StoreDto createStore(StoreDto storeDto);

    // Get store by ID
    StoreDto getStoreById(Long id);

    // Get all stores (SUPER_ADMIN)
    List<StoreDto> getAllStore();

    // Get store owned by current admin
    Store getStoreByAdmin();

    // Moderate store (SUPER_ADMIN)
    StoreDto moderateStore(Long id, StoreStatus status);

    // Update store (OWNER ONLY)
    StoreDto updateStore(Long id, StoreDto storeDto);

    // Delete store (OWNER ONLY)
    void deleteStore(Long id);

    // Get store for employee
    StoreDto getStoreByEmployee();
}
