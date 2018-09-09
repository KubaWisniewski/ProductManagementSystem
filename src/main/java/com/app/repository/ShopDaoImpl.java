package com.app.repository;

import com.app.model.Shop;
import com.app.model.Trade;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopDaoImpl extends AbstractGenericDao<Shop> implements ShopDao {
    @Override
    public Optional<Shop> getShopByNameAndCountry(String name, String countryName) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        Optional<Shop> shop = Optional.empty();

        try {
            tx.begin();
            Query query = session.createQuery("select s from Shop s where s.name=:name and s.country.name=:countryName");
            query.setParameter("name", name);
            query.setParameter("countryName", countryName);
            shop = Optional.ofNullable((Shop) query.uniqueResult());
            tx.commit();
            if (shop.isPresent())
                return shop;
        } catch (Exception c) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return shop;
    }

    @Override
    public List<Shop> getShopsByCountry() {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        List<Shop> res = new ArrayList<>();
        try {
            tx.begin();
            Query query = session.createQuery("SELECT s from Shop s join s.stockSet ss join ss.product p join p.producer pp where pp.country.name!=s.country.name group by s.name");
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
}
