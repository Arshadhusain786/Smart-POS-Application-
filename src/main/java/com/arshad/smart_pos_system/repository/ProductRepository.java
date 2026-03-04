package com.arshad.smart_pos_system.repository;

import com.arshad.smart_pos_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>
{

    List<Product> findByName(String name);

    List<Product> findByCategory(String category);

    List<Product> findByCategoryAndBrand(String category, String brand);

    Product findByNameAndStoreId(String name, Long storeId);

    List<Product> findByStoreId(Long storeId);

    List<Product> findByStoreIdAndCategory(Long storeId, String category);

    List<Product> findByStoreIdAndNameContainingIgnoreCase(Long storeId, String name);

    List<Product> findByNameContainingIgnoreCase(String name);


    // Advanced POS Search
    @Query("SELECT p FROM Product p WHERE p.store.id = :storeId AND (" +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.sku) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Product> searchByKeyword(@Param("storeId") Long storeId,
                                  @Param("query") String query);
}