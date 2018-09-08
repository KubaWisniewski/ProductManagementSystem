package com.app.repository;


import com.app.model.Customer;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface CustomerDao extends GenericDao<Customer> {
    Optional<Customer> getCustomerByNameSurnameCountry(String name, String surname, String country);

    void getCustomersWithProductsWithTheSameCountry();
}
