package com.app.repository;

import com.app.model.Stock;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface StockDao extends GenericDao<Stock> {
    Optional<Stock> getStockByProductAndShop(String productName, String categoryName, String shopName,String countryName);
}
