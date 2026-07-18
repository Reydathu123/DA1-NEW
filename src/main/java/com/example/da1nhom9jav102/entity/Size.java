package com.example.da1nhom9jav102.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "size_value", nullable = false, unique = true)
    private Double sizeValue;
    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Variant> variants;

    public Size(Double sizeValue) {
        this.sizeValue = sizeValue;
    }
}