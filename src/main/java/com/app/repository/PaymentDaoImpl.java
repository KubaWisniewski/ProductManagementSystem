package com.app.repository;

import com.app.model.EPayment;
import com.app.model.Payment;
import com.app.repository.generic.AbstractGenericDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class PaymentDaoImpl extends AbstractGenericDao<Payment> implements PaymentDao {
    @Override
    public Optional<Payment> getPaymentByName(String name) {
        Session session = getFactory().openSession();
        Transaction tx = session.getTransaction();
        Optional<Payment> payment = Optional.empty();

        try {
            tx.begin();
            Query query = session.createQuery("select p from Payment p where p.payment=:payment");
            query.setParameter("payment", EPayment.valueOf(name));
            payment = Optional.ofNullable((Payment) query.uniqueResult());
            tx.commit();
            if (payment.isPresent())
                return payment;
        } catch (Exception c) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return payment;
    }

}
