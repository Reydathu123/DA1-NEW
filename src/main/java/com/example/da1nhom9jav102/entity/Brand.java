package com.example.da1nhom9jav102.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Column(name = "logo", length = 250)
    private String logo;

    @Column(name = "active")
    private Boolean active;
}
