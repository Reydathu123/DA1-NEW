package com.example.da1nhom9jav102.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rackets")
public class Racket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "image", length = 250)
    private String image;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "material", length = 100)
    private String material;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "racket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Variant> variants;

    @OneToMany(mappedBy = "racket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    public Racket(String name, Double price, Double discount, String image,
                  String description, String material, String gender, Boolean active) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.description = description;
        this.material = material;
        this.gender = gender;
        this.active = active;
    }

    // Tính giá sau giảm
    public Double getDiscountedPrice() {
        if (discount != null && discount > 0) {
            return price * (1 - discount / 100);
        }
        return price;
    }
}