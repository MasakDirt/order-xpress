package com.micro.flow.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Document("t-shirt")
public class TShirt extends Clothes {

    @NotEmpty(message = "Sizes must be included!")
    private List<SampleSize> sizes;

    @NotEmpty(message = "Colors must be included!")
    private List<String> colors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TShirt tShirt = (TShirt) o;
        return Objects.equals(sizes, tShirt.sizes) && Objects.equals(colors, tShirt.colors);
    }

    @Override
    public int hashCode() {
        return 23 * Objects.hash(super.hashCode(), sizes, colors);
    }
}
