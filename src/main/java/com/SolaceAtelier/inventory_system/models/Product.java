package com.SolaceAtelier.inventory_system.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Requirement: Validation rejects bad data
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Brand is required")
    private String brand;

    @Min(value = 1, message = "Price must be at least 1.00")
    private Double price;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    // Requirement: Must include a timestamp
    @CreationTimestamp
    private LocalDateTime createdAt;
}