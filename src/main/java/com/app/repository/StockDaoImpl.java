package com.app.repository;

import com.app.model.Stock;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class StockDaoImpl extends AbstractGenericDao<Stock> implements StockDao {
    @Override
    public Optional<Stock> getStockByProductAndShop(String productName, String categoryName, String shopName) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        Optional<Stock> stock = Optional.empty();
        ProductDao productDao = new ProductDaoImpl();
        ShopDao shopDao = new ShopDaoImpl();
        try {
            tx.begin();
            Query query = session.createQuery("select s from Stock s where s.product=:product and s.shop=:shop");
            query.setParameter("product", productDao.getProductByNameAndCategory(productName, categoryName).get());
            query.setParameter("shop", shopDao.getShopByName(shopName).get());
            stock = Optional.ofNullable((Stock) query.uniqueResult());
            tx.commit();
            if (stock.isPresent())
                return stock;
        } catch (Exception c) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return stock;
    }
}
