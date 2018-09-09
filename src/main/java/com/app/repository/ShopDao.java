package com.app.repository;

import com.app.model.Shop;
import com.app.repository.generic.GenericDao;

import java.util.List;
import java.util.Optional;

public interface ShopDao extends GenericDao<Shop> {
    Optional<Shop> getShopByNameAndCountry(String name,String countryName);

    List<Shop> getShopsByCountry();
}
