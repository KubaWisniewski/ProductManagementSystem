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
public class Trade {
    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "^[A-Z\\s]+", message = "TRADE;NAME IS NOT CORRECT")
    private String name;
    @OneToMany(mappedBy = "trade",cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Set<Producer>producerSet=new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(id, trade.id) &&
                Objects.equals(name, trade.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
