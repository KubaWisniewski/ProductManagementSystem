package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double price;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;
    @OneToMany(mappedBy = "product",cascade = CascadeType.PERSIST)
    private Set<CustomerOrder>customerOrderSet=new HashSet<>();
    @OneToMany(mappedBy = "product",cascade = CascadeType.PERSIST)
    private Set<Stock>stockSet=new HashSet<>();
}
