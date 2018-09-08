package com.app.repository;

import com.app.model.Payment;
import com.app.repository.generic.GenericDao;

import java.util.Optional;

public interface PaymentDao extends GenericDao<Payment> {
    Optional<Payment> getPaymentByName(String name);
}
