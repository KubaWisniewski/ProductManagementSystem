package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name","surname","country_id"}))
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @Min(value = 18,message = "CUSTOMER;AGE IS NOT CORRECT")
    private int age;
    @Pattern(regexp = "^[A-Z\\s]+",message = "CUSTOMER;NAME IS NOT CORRECT")
    private String name;
    @Pattern(regexp = "^[A-Z\\s]+",message = "CUSTOMER;SURNAME IS NOT CORRECT")
    private String surname;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "country_id")
    private Country country;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Set<CustomerOrder> customerOrderSet=new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age &&
                Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(surname, customer.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, name, surname);
    }
}
