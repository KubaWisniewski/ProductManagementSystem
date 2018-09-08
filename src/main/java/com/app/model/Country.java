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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Country {
    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "^[A-Z\\s]+", message = "COUNTRY;NAME IS NOT CORRECT")
    private String name;
    @OneToMany(mappedBy = "country", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Customer> customerSet = new HashSet<>();
    @OneToMany(mappedBy = "country", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Producer> producerSet = new HashSet<>();
    @OneToMany(mappedBy = "country", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Shop> shopSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Country:" +
                " name=" + name;
    }
}
