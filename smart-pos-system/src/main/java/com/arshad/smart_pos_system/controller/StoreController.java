package com.arshad.smart_pos_system.controller;

import com.arshad.smart_pos_system.domain.StoreStatus;
import com.arshad.smart_pos_system.dto.StoreDto;
import com.arshad.smart_pos_system.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // ============================
    // CREATE STORE (ADMIN)
    // ============================
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoreDto> createStore(
            @RequestBody StoreDto storeDto) {

        return ResponseEntity.ok(storeService.createStore(storeDto));
    }

    // ============================
    // GET STORE BY ID
    // ============================
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id) {

        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    // ============================
    // GET ALL STORES (SUPER_ADMIN)
    // ============================
    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<StoreDto>> getAllStores() {

        return ResponseEntity.ok(storeService.getAllStore());
    }

    // ============================
    // GET STORE BY ADMIN (OWNER)
    // ============================
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoreDto> getStoreByAdmin() {

        return ResponseEntity.ok(
                storeService.getStoreByAdmin() != null
                        ? storeService.getStoreById(
                        storeService.getStoreByAdmin().getId())
                        : null
        );
    }

    // ============================
    // UPDATE STORE (OWNER ONLY)
    // ============================
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoreDto> updateStore(
            @PathVariable Long id,
            @RequestBody StoreDto storeDto)
    {


        return ResponseEntity.ok(storeService.updateStore(id, storeDto));
    }

    // ============================
    // DELETE STORE (OWNER ONLY)
    // ============================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStore(@PathVariable Long id) {

        storeService.deleteStore(id);
        return ResponseEntity.ok("Store deleted successfully");
    }

    // ============================
    // MODERATE STORE (SUPER_ADMIN)
    // ============================
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<StoreDto> moderateStore(
            @PathVariable Long id,
            @RequestParam StoreStatus status) {

        return ResponseEntity.ok(storeService.moderateStore(id, status));
    }

    // ============================
    // GET STORE BY EMPLOYEE
    // ============================
    @GetMapping("/employee")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<StoreDto> getStoreByEmployee() {

        return ResponseEntity.ok(storeService.getStoreByEmployee());
    }
}
