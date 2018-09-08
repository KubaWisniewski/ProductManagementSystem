package com.app.repository;

import com.app.model.Country;
import com.app.model.Trade;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class TradeDaoImpl extends AbstractGenericDao<Trade> implements TradeDao {
    @Override
    public Optional<Trade> getTradeByName(String name) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        Optional<Trade> trade = Optional.empty();

        try {
            tx.begin();
            Query query = session.createQuery("select t from Trade t where t.name=:name");
            query.setParameter("name", name);
            trade = Optional.ofNullable((Trade) query.uniqueResult());
            tx.commit();
            if (trade.isPresent())
                return trade;
        } catch (Exception c) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return trade;
    }
}
