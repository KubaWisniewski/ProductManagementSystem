package com.app.repository;

import com.app.model.Country;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.Queue;

public class CountryDaoImpl extends AbstractGenericDao<Country> implements CountryDao {
    @Override
    public Optional<Country> getCountryByName(String name) {
        Session session=getFactory().openSession();
        Transaction tx=session.getTransaction();
        Optional<Country> country=Optional.empty();

        try{
            tx.begin();
            Query query=session.createQuery("select c from Country c where c.name=:name");
            query.setParameter("name",name);
            country=Optional.ofNullable((Country)query.uniqueResult());
            tx.commit();
            if(country.isPresent())
                return country;
        }catch (Exception c){
            if(tx!=null){
                tx.rollback();
            }
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return country;
    }
}
