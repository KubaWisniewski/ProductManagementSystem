package com.app.repository;

import com.app.model.Country;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface CountryDao extends GenericDao<Country> {
    Optional<Country> getCountryByName(String name);
}
