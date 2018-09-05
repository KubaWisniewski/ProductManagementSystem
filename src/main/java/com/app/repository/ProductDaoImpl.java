package com.app.repository;

import com.app.model.Product;
import com.app.model.Trade;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class ProductDaoImpl extends AbstractGenericDao<Product> implements ProductDao {
    @Override
    public Optional<Product> getProductByName(String name) {
        Session session=getFactory().openSession();
        Transaction tx=session.getTransaction();
        Optional<Product> product=Optional.empty();

        try{
            tx.begin();
            Query query=session.createQuery("select p from Product p where p.name=:name");
            query.setParameter("name",name);
            product=Optional.ofNullable((Product) query.uniqueResult());
            tx.commit();
            if(product.isPresent())
                return product;
        }catch (Exception c){
            if(tx!=null){
                tx.rollback();
            }
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return product;
    }
}
