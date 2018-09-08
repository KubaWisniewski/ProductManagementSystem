package com.app.repository;

import com.app.model.Category;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class CategoryDaoImpl extends AbstractGenericDao<Category> implements CategoryDao {
    @Override
    public Optional<Category> getCategotyByName(String name) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        Optional<Category> category = Optional.empty();
        try {
            tx.begin();
            Query query = session.createQuery("select c from Category c where c.name=:name");
            query.setParameter("name", name);
            category = Optional.ofNullable((Category) query.uniqueResult());
            tx.commit();
            if (category.isPresent())
                return category;
        } catch (Exception c) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return category;
    }
}
