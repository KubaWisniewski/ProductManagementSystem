package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue
    private Long id;
    @Positive(message = "STOCK;QUANTITY IS NOT POSITIVE")
    private Integer quantity;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(id, stock.id) &&
                Objects.equals(quantity, stock.quantity) &&
                Objects.equals(product, stock.product) &&
                Objects.equals(shop, stock.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, product, shop);
    }

    @Override
    public String toString() {
        return "Stock:" +
                " quantity=" + quantity +
                ", product=" + product +
                ", shop=" + shop;
    }
}
