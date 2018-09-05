package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;
    @DecimalMin(value = "0.0",inclusive = true,message = "CUSTOMERORDER;DISCOUNT IS NOT CORRECT")
    @DecimalMax(value = "1.0",inclusive = true,message = "CUSTOMERORDER;DISCOUNT IS NOT CORRECT")
    private Double discount;
    @PastOrPresent
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrder that = (CustomerOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, discount, date);
    }
}

