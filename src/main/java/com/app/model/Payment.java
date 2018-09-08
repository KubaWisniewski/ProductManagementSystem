package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"payment"}))
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private EPayment payment;
    @OneToMany(mappedBy = "payment", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<CustomerOrder> customerOrderSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment1 = (Payment) o;
        return Objects.equals(id, payment1.id) &&
                payment == payment1.payment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment);
    }

    @Override
    public String toString() {
        return "Payment:" +
                " payment=" + payment;
    }
}
