package com.app.repository;

import com.app.model.EGuarantee;
import com.app.model.Product;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl extends AbstractGenericDao<Product> implements ProductDao {
    @Override
    public Optional<Product> getProductByNameAndCategory(String name, String category) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        Optional<Product> product = Optional.empty();

        try {
            tx.begin();
            Query query = session.createQuery("select p from Product p where p.name=:name and p.category.name=:category");
            query.setParameter("name", name);
            query.setParameter("category", category);
            product = Optional.ofNullable((Product) query.uniqueResult());
            tx.commit();
            if (product.isPresent())
                return product;
        } catch (Exception c) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return product;
    }

    @Override
    public List<Product> getProductsByMaxPriceInCategory() {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        List<Product> res = new ArrayList<>();
        try {
            tx.begin();
            Query query = session.createQuery("select pro from Product pro join pro.category cat where pro.price=(select max(prod.price)from Product prod where prod.category=pro.category)");
            res = query.getResultList();
            //res.forEach(x -> System.out.println(x));
            tx.commit();
            return res;
        } catch (Exception e) {
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

    @Override
    public List<Product> getProductsByCustomerCountryAndAge(String country, Integer ageMin, Integer ageMax) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        List<Product> res = new ArrayList<>();
        try {
            tx.begin();
            Query query = session.createQuery("select pro from CustomerOrder co join co.product pro join co.customer cu where cu.age>=:agemin and cu.age<=:agemax and cu.country.name=:countryname order by pro.price DESC");
            query.setParameter("agemin", ageMin);
            query.setParameter("agemax", ageMax);
            query.setParameter("countryname", country);
            res = query.getResultList();
            //res.forEach(x -> System.out.println(x));
            tx.commit();
            return res;
        } catch (Exception e) {
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

    @Override
    public List<Product> getProductsByGuarantee(EGuarantee guarantee) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        List<Product> res = new ArrayList<>();
        try {
            tx.begin();
            Query query = session.createQuery("select pro from Product pro  join pro.eGuaranteeSet g where g=:enumeration group by pro.category.name");
            query.setParameter("enumeration", guarantee);
            res = query.getResultList();
            //res.forEach(x -> System.out.println(x));
            tx.commit();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public List<Product> getProductsWithCustomerNameSurnameAndCountry(String name, String surname, String country) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        List<Product> res = new ArrayList<>();
        try {
            tx.begin();
            Query query = session.createQuery("select pro from Customer c join c.customerOrderSet co join co.product pro where c.name=:name and c.surname=:surname and c.country.name=:country group by pro.producer.name");
            query.setParameter("name", name);
            query.setParameter("surname", surname);
            query.setParameter("country", country);
            res = query.getResultList();
            //res.forEach(x -> System.out.println(x));
            tx.commit();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
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
