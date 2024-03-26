package com.micro.flow.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Document("outerwear")
public class Outerwear extends Clothes {

    @NotEmpty(message = "Sizes must be included!")
    private List<SampleSize> sizes;

    @NotEmpty(message = "Colors must be included!")
    private List<String> colors;

    @NotNull(message = "Type of outerwear must be included!")
    private OuterwearType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Outerwear outerwear = (Outerwear) o;
        return Objects.equals(sizes, outerwear.sizes) &&
                Objects.equals(colors, outerwear.colors) && type == outerwear.type;
    }

    @Override
    public int hashCode() {
        return 17 * Objects.hash(super.hashCode(), sizes, colors, type);
    }

    public enum OuterwearType {
        T_SHIRT, PANTS, JACKET
    }

}
