package com.micro.flow.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Table(value = "bag")
@ToString(of = "id")
public class Bag {

    @PrimaryKey
    private UUID id;

    @Column(value = "total_price")
    @NotNull(message = "Total price cannot be null!")
    @Min(value = 0, message = "Total price cannot be lower that 0!")
    private BigDecimal totalPrice;

    @Column
    private String username;

    @Column(value = "clothes_id's")
    private List<Long> clothesIds;

    public Bag() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bag bag = (Bag) o;
        return id != null && this.id.equals(bag.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
