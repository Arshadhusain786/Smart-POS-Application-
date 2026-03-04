package com.arshad.smart_pos_system.repository;

import com.arshad.smart_pos_system.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long>
{
   Store findByStoreAdminId(Long id);
}
