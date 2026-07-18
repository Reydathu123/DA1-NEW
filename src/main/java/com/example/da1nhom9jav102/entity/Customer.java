package com.example.da1nhom9jav102.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "membership", length = 50)
    private String membership;

    @Column(name = "points")
    private Integer points;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Customer(String membership, Integer points, User user) {
        this.membership = membership;
        this.points = points;
        this.user = user;
    }
}