package com.app.repository;

import com.app.model.Producer;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface ProducerDao extends GenericDao<Producer> {
    Optional<Producer> getProducerByNameAndCountry(String name,String countryName);
}
