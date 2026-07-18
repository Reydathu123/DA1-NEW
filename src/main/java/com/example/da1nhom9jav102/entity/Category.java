package com.example.da1nhom9jav102.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",length = 250, nullable = false)
    private String name;

    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Racket> rackets;

    public Category(String name, Boolean active) {
        this.name = name;
        this.active = active;
    }
}
