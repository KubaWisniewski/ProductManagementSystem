package com.app.repository;

import com.app.model.Product;
import com.app.model.Shop;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface ProductDao extends GenericDao<Product> {
    Optional<Product> getProductByName(String name);
}
