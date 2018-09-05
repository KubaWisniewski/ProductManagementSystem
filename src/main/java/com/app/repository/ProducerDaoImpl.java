package com.app.repository;

import com.app.model.Producer;
import com.app.model.Trade;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class ProducerDaoImpl extends AbstractGenericDao<Producer> implements ProducerDao {
    @Override
    public Optional<Producer> getProducerByNameAndCountry(String name,String countryName) {
        Session session=getFactory().openSession();
        Transaction tx=session.getTransaction();
        Optional<Producer> producer=Optional.empty();
        CountryDao countryDao=new CountryDaoImpl();

        try{
            tx.begin();
            Query query=session.createQuery("select p from Producer p where p.name=:name and p.country_id=:country_id");
            query.setParameter("name",name);
            query.setParameter("country_id",countryDao.getCountryByName(countryName).get().getId());
            producer=Optional.ofNullable((Producer) query.uniqueResult());
            tx.commit();
            if(producer.isPresent())
                return producer;
        }catch (Exception c){
            if(tx!=null){
                tx.rollback();
            }
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return producer;
    }
}
