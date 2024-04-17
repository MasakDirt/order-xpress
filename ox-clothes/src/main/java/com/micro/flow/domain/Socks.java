package com.micro.flow.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "socks")
public class Socks extends Clothes {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @NotNull(message = "Size must be include!")
    @Column(nullable = false, updatable = false)
    private SampleSize size;

    @ElementCollection(fetch = EAGER)
    @NotEmpty(message = "Colors must be include!")
    @CollectionTable(name = "socks_colors",
            joinColumns = @JoinColumn(name = "socks_id"))
    private List<String> colors;

}
