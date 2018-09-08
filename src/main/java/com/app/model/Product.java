package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "category_id", "producer_id"}))
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "^[A-Z\\s]+", message = "PRODUCT;NAME IS NOT CORRECT")
    private String name;
    @Positive(message = "PRODUCT;PRICE IS NOT POSITIV")
    private Double price;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "producer_id")
    private Producer producer;
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<CustomerOrder> customerOrderSet = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Stock> stockSet = new HashSet<>();
    @ElementCollection(targetClass = EGuarantee.class)
    @CollectionTable(
            name = "guarantee_components",
            joinColumns = {@JoinColumn(name = "product_id")}
    )
    @Column(name = "guarantee_component")
    @Enumerated(EnumType.STRING)
    private Set<EGuarantee> eGuaranteeSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(category, product.category) &&
                Objects.equals(producer, product.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category, producer);
    }

    @Override
    public String toString() {
        return "Product:" +
                " name=" + name +
                ", price=" + price+
                ", category=" + category+
                ", producer=" + producer;
    }
}
