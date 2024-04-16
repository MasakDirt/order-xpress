package com.micro.flow.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Getter
@Setter
@Entity
@Table(name = "outerwear")
public class Outerwear extends Clothes {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter(PRIVATE)
    @ElementCollection(fetch = EAGER)
    @NotEmpty(message = "Sizes must be included!")
    @CollectionTable(name = "outerwear_sizes",
            joinColumns = @JoinColumn(name = "outerwear_id"))
    private List<SampleSize> sizes;

    @Setter(PRIVATE)
    @ElementCollection
    @NotEmpty(message = "Colors must be included!")
    @CollectionTable(name = "outerwear_colors",
            joinColumns = @JoinColumn(name = "outerwear_id"))
    private List<String> colors;

    @NotNull(message = "Type of outerwear must be included!")
    private OuterwearType type;

    public enum OuterwearType {
        T_SHIRT, PANTS, JACKET
    }

}
