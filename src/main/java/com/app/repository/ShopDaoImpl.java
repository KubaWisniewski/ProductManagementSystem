package com.app.repository;

import com.app.model.Shop;
import com.app.model.Trade;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class ShopDaoImpl extends AbstractGenericDao<Shop> implements ShopDao {
    @Override
    public Optional<Shop> getShopByName(String name) {
        Session session=getFactory().openSession();
        Transaction tx=session.getTransaction();
        Optional<Shop> shop=Optional.empty();

        try{
            tx.begin();
            Query query=session.createQuery("select s from Shop s where s.name=:name");
            query.setParameter("name",name);
            shop=Optional.ofNullable((Shop) query.uniqueResult());
            tx.commit();
            if(shop.isPresent())
                return shop;
        }catch (Exception c){
            if(tx!=null){
                tx.rollback();
            }
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return shop;
    }
}
