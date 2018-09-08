package com.app.repository;

import com.app.model.CustomerOrder;
import com.app.repository.generic.GenericDao;

import java.time.LocalDate;
import java.util.List;

public interface CustomerOrderDao extends GenericDao<CustomerOrder> {
    List<CustomerOrder> getCustomerOrdersByPriceAndBetweenDates(LocalDate dateMin, LocalDate dateMax, Double price);

}
