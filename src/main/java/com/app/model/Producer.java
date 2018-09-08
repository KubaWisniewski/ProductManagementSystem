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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "trade_id", "country_id"}))
public class Producer {
    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "^[A-Z\\s]+", message = "PRODUCER;NAME IS NOT CORRECT")
    private String name;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "trade_id")
    private Trade trade;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "country_id")
    private Country country;
    @OneToMany(mappedBy = "producer", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Product> productSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id) &&
                Objects.equals(name, producer.name) &&
                Objects.equals(trade, producer.trade) &&
                Objects.equals(country, producer.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, trade, country);
    }

    @Override
    public String toString() {
        return "Producer:" +
                " name=" + name +
                ", trade=" + trade +
                ", country=" + country;
    }
}
