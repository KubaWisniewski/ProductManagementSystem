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
public class Country {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "country", cascade = CascadeType.PERSIST)
    private Set<Customer>customerSet=new HashSet<>();
    @OneToMany(mappedBy = "country",cascade = CascadeType.PERSIST)
    private Set<Producer>producerSet=new HashSet<>();
    @OneToMany(mappedBy = "country",cascade = CascadeType.PERSIST)
    private Set<Shop>shopSet=new HashSet<>();
}
