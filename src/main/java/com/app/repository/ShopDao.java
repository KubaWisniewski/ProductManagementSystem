package com.app.repository;

import com.app.model.Shop;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface ShopDao extends GenericDao<Shop> {
    Optional<Shop> getShopByName(String name);
}
