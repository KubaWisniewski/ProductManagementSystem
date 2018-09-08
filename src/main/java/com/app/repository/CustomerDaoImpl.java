package com.app.repository;

import com.app.model.Customer;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl extends AbstractGenericDao<Customer> implements CustomerDao {
    @Override
    public Optional<Customer> getCustomerByNameSurnameCountry(String name, String surname, String country) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        CountryDao countryDao = new CountryDaoImpl();
        Optional<Customer> customer = Optional.empty();
        try {
            tx.begin();
            Query query = session.createQuery("select c from Customer c where c.name=:name and c.surname=:surname and c.country=:country");
            query.setParameter("name", name);
            query.setParameter("surname", surname);
            query.setParameter("country", countryDao.getCountryByName(country).get());
            customer = Optional.ofNullable((Customer) query.uniqueResult());
            tx.commit();
            if (customer.isPresent())
                return customer;
        } catch (Exception c) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }

    @Override
    public void getCustomersWithProductsWithTheSameCountry() {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            Query query = session.createQuery("select c from Customer c join c.customerOrderSet co where c.country.name=co.product.producer.country.name group by c.name");
            List<Customer> res = query.getResultList();
            res.forEach(x -> System.out.println(x.toString()));
            tx.commit();
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

    }
}

