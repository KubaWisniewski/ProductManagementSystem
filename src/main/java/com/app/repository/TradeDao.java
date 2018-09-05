package com.app.repository;

import com.app.model.Trade;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface TradeDao extends GenericDao<Trade> {
    Optional<Trade> getTradeByName(String name);
}
