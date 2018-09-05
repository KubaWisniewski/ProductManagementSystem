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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"payment"}))
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private EPayment payment;
    @OneToMany(mappedBy = "payment",cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Set<CustomerOrder>customerOrderSet=new HashSet<>();
}
