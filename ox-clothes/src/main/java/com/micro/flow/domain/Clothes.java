package com.micro.flow.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "clothes")
@ToString(of = "productName")
@EqualsAndHashCode(of = "productName")
public class Clothes {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NaturalId
    @NotEmpty(message = "Product name must be included!")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull(message = "Price must be included!")
    @Column(nullable = false)
    @Min(value = 0, message = "Price cannot be lower that 0!")
    private BigDecimal price;

    @Enumerated(STRING)
    @Column(nullable = false)
    @NotNull(message = "Sex must be included!")
    private Sex sex;

    @Column(nullable = false)
    @NotEmpty(message = "Describe this product!")
    private String description;

    @Column(nullable = false)
    @Min(value = 0, message = "Amount of clothes cannot be lower than 0!")
    private int availableAmount;

    @Enumerated(STRING)
    @ElementCollection(fetch = EAGER)
    @NotEmpty(message = "Sizes must be included!")
    @CollectionTable(name = "clothes_sizes",
            joinColumns = @JoinColumn(name = "clothes_id"))
    private List<SampleSize> sizes;

    @ElementCollection(fetch = EAGER)
    @NotNull(message = "Colors must be included!")
    @CollectionTable(name = "clothes_colors",
            joinColumns = @JoinColumn(name = "clothes_id"))
    private List<String> colors;

    @Enumerated(STRING)
    @NotNull(message = "Type of clothes must be included!")
    private Type type;

    @Column(name = "seller_username", nullable = false)
    @NotEmpty(message = "Seller username cannot be empty!")
    private String sellerUsername;

}
