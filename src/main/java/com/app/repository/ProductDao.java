package com.app.repository;

import com.app.model.EGuarantee;
import com.app.model.Product;
import com.app.repository.generic.GenericDao;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends GenericDao<Product> {
    Optional<Product> getProductByNameAndCategory(String name, String category);

    List<Product> getProductsByMaxPriceInCategory();

    List<Product> getProductsByCustomerCountryAndAge(String country, Integer ageMin, Integer ageMax);

    List<Product> getProductsByGuarantee(EGuarantee... guarantee);

    List<Product> getProductsWithCustomerNameSurnameAndCountry(String name, String surname, String country);
}
