package com.app.repository;

import com.app.model.Producer;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerDaoImpl extends AbstractGenericDao<Producer> implements ProducerDao {
    @Override
    public Optional<Producer> getProducerByNameAndCountry(String name, String countryName) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        Optional<Producer> producer = Optional.empty();

        try {
            tx.begin();
            Query query = session.createQuery("select p from Producer p where p.name=:name and p.country.name=:country");
            query.setParameter("name", name);
            query.setParameter("country", countryName);
            producer = Optional.ofNullable((Producer) query.uniqueResult());
            tx.commit();
            if (producer.isPresent())
                return producer;
        } catch (Exception c) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return producer;
    }

    @Override
    public List<Producer> getProducersByTradeAndQuantity(String trade, Integer quantity) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        List<Producer> res=new ArrayList<>();
        try {
            tx.begin();
            Query query = session.createQuery("SELECT p from Stock s join s.product pp join pp.producer p join p.trade t where t.name=:brandName GROUP BY p.name HAVING sum(s.quantity)>:quantity");
            query.setParameter("brandName", trade);
            query.setParameter("quantity", new Long(quantity));
            res=query.getResultList();
            //res.stream().forEach(x -> System.out.println(x));
            tx.commit();
            return res;
        } catch (Exception c) {
            c.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return res;
    }
}
