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
@Document("socks")
public class Socks {

    @NotNull(message = "Size must be include!")
    private SampleSize size;

    @NotEmpty(message = "Colors must be include!")
    private List<String> colors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return size == socks.size && Objects.equals(colors, socks.colors);
    }

    @Override
    public int hashCode() {
        return 21 * Objects.hash(size, colors);
    }
}
