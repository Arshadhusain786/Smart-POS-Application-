package com.arshad.smart_pos_system.service.impl;

import com.arshad.smart_pos_system.domain.StoreStatus;
import com.arshad.smart_pos_system.dto.StoreDto;
import com.arshad.smart_pos_system.exception.UserException;
import com.arshad.smart_pos_system.mapper.StoreMapper;
import com.arshad.smart_pos_system.model.Store;
import com.arshad.smart_pos_system.model.StoreContact;
import com.arshad.smart_pos_system.model.User;
import com.arshad.smart_pos_system.repository.StoreRepository;
import com.arshad.smart_pos_system.service.StoreService;
import com.arshad.smart_pos_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    // ============================
    // CREATE STORE (ADMIN)
    // ============================
    @Override
    public StoreDto createStore(StoreDto storeDto) {

        User currentUser = userService.getCurrentUser();

        if (!currentUser.getRole().name().equals("ROLE_ADMIN")) {
            throw new UserException("Only ADMIN can create store");
        }

        Store store = StoreMapper.toEntity(storeDto, currentUser);

        store.setStatus(StoreStatus.PENDING);

        Store savedStore = storeRepository.save(store);

        return StoreMapper.toDto(savedStore);
    }

    // ============================
    // GET STORE BY ID
    // ============================
    @Override
    public StoreDto getStoreById(Long id) {

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new UserException("Store not found"));

        return StoreMapper.toDto(store);
    }

    // ============================
    // GET ALL STORES
    // ============================
    @Override
    public List<StoreDto> getAllStore() {

        return storeRepository.findAll()
                .stream()
                .map(StoreMapper::toDto)
                .collect(Collectors.toList());
    }

    // ============================
    // GET STORE BY ADMIN
    // ============================
    @Override
    public Store getStoreByAdmin() {

        User admin = userService.getCurrentUser();

        return storeRepository.findByStoreAdminId(admin.getId());
    }

    // ============================
    // MODERATE STORE (SUPER_ADMIN)
    // ============================
    @Override
    public StoreDto moderateStore(Long id, StoreStatus status) {

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new UserException("Store not found"));

        store.setStatus(status);

        return StoreMapper.toDto(storeRepository.save(store));
    }

    // ============================
    // UPDATE STORE (OWNER ONLY)
    // ============================
    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) {

        User currentUser = userService.getCurrentUser();

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new UserException("Store not found"));

        // 🔥 Ownership validation
        if (!store.getStoreAdmin().getId().equals(currentUser.getId())) {
            throw new UserException("You are not allowed to update this store");
        }

        store.setBrand(storeDto.getBrand());
        store.setDescription(storeDto.getDescription());

        if (storeDto.getStoreType() != null) {
            store.setStoreType(storeDto.getStoreType());
        }

        if (storeDto.getContact() != null) {

            StoreContact contact = StoreContact.builder()
                    .address(storeDto.getContact().getAddress())
                    .phone(storeDto.getContact().getPhone())
                    .email(storeDto.getContact().getEmail())
                    .build();

            store.setContact(contact); // 🔥 FIXED
        }

        return StoreMapper.toDto(storeRepository.save(store));
    }

    // ============================
    // DELETE STORE (OWNER ONLY)
    // ============================
    @Override
    public void deleteStore(Long id) {

        User currentUser = userService.getCurrentUser();

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new UserException("Store not found"));

        if (!store.getStoreAdmin().getId().equals(currentUser.getId())) {
            throw new UserException("You are not allowed to delete this store");
        }

        storeRepository.delete(store);
    }

    // ============================
    // GET STORE BY EMPLOYEE
    // ============================
    @Override
    public StoreDto getStoreByEmployee() {

        User currentUser = userService.getCurrentUser();

        if (currentUser.getStore() == null) {
            throw new UserException("Employee is not assigned to any store");
        }

        return StoreMapper.toDto(currentUser.getStore());
    }
}
