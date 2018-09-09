package com.app.repository;

import com.app.model.CustomerOrder;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrderDaoImpl extends AbstractGenericDao<CustomerOrder> implements CustomerOrderDao {

    @Override
    public List<CustomerOrder> getCustomerOrdersByPriceAndBetweenDates(LocalDate dateMin, LocalDate dateMax, Double price) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        List<CustomerOrder> res = new ArrayList<>();
        try {
            tx.begin();
            Query query = session.createQuery("select co from CustomerOrder co where co.date>=:dateMin and co.date<=:dateMax and (co.product.price*co.quantity*co.discount)>=:price");
            query.setParameter("dateMin", dateMin);
            query.setParameter("dateMax", dateMax);
            query.setParameter("price", price);
            res = query.getResultList();

            //res.forEach(x -> System.out.println(x.toString()));
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
