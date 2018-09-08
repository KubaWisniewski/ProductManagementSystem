package com.app.repository;

import com.app.model.Category;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface CategoryDao extends GenericDao<Category> {
    Optional<Category> getCategotyByName(String name);
}
