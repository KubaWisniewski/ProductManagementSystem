package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name","country_id"}))
public class Shop {
    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "^[A-Z\\s]+",message = "SHOP;NAME IS NOT CORRECT")
    private String name;
    @OneToMany(mappedBy = "shop",cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Set<Stock>stockSet=new HashSet<>();
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "country_id")
    private Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return Objects.equals(id, shop.id) &&
                Objects.equals(name, shop.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
