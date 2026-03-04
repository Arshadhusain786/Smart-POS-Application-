package com.arshad.smart_pos_system.model;

import com.arshad.smart_pos_system.domain.StoreStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false , unique = true)
    private String sku;

    private String description;

    private Double mrp;

    private Double sellingPrice;

    private String image;

    private String brand;

    private String category;

    @ManyToOne // one store have many products but one product have only one store
    private Store store;

    private LocalDateTime  createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate()
    {
        createdAt = LocalDateTime.now();

    }
    @PreUpdate
    protected void onUpdate()
    {
        updatedAt = LocalDateTime.now();

    }

}
