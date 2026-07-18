package com.example.da1nhom9jav102.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "variants")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "grip_size", length = 10)
    private String gripSize;

    @Column(name = "flex", length = 30)
    private String flex;

    @Column(name = "balance", length = 30)
    private String balance;

    @Column(name = "weight")
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "racket_id")
    private Racket racket;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    public Variant(Integer stock, String gripSize, String flex, String balance, Double weight) {
        this.stock = stock;
        this.gripSize = gripSize;
        this.flex = flex;
        this.balance = balance;
        this.weight = weight;
    }
}