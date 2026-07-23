package com.example.da1nhom9jav102.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "full_name", length = 60)
    private String fullName;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "role")
    private Boolean role; // true = ADMIN, false = USER

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    public User(String email, String password, String fullName, String phone,
                String address, Boolean role, Boolean active) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.active = active;
    }

    public boolean isAdmin() {
        return role != null && role;
    }
}