package com.example.da1nhom9jav102.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "total")
    private Double total;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "shipping_address", length = 200, nullable = false)
    private String shippingAddress;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    public Order(String code, LocalDate createdAt, Double total, String status,
                 String paymentMethod, String shippingAddress) {
        this.code = code;
        this.createdAt = createdAt;
        this.total = total;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
    }
}