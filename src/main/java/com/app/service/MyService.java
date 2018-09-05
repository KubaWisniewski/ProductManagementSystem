package com.app.service;


import com.app.model.*;
import com.app.repository.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class MyService {
    private CustomerDao customerDao = new CustomerDaoImpl();
    private CountryDao countryDao = new CountryDaoImpl();
    private ShopDao shopDao = new ShopDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private ErrorsDao errorsDao = new ErrorsDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private StockDao stockDao = new StockDaoImpl();
    private TradeDao tradeDao = new TradeDaoImpl();
    private CustomerOrderDao customerOrderDao = new CustomerOrderDaoImpl();
    private ProducerDao producerDao = new ProducerDaoImpl();
    private PaymentDao paymentDao=new PaymentDaoImpl();
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    public void addCustomer(String name, String surname, Integer age, String countryName) {
        try {
            Set<ConstraintViolation<Customer>> constraintViolations;
            if (!countryDao.getCountryByName(countryName).isPresent()) {
                throw new RuntimeException("COUNTRY;COUNTRY IS NOT PRESENT");
            }
            Customer customer = Customer
                    .builder()
                    .name(name)
                    .surname(surname)
                    .age(age)
                    .country(countryDao.getCountryByName(countryName).get())
                    .build();
            constraintViolations = validator.validate(customer);
            if (constraintViolations.size() > 0) {
                for (ConstraintViolation<?> violation : constraintViolations)
                    errorsDao.add(Errors.builder().message(violation.getMessage()).date(LocalDate.now()).build());
            } else {
                customerDao.add(customer);
            }
        } catch (RuntimeException r) {
            errorsDao.add(Errors.builder().message(r.getMessage()).date(LocalDate.now()).build());
        }
    }

    public void addShop(String name, String countryName) {
        try {
            Set<ConstraintViolation<Shop>> constraintViolations;
            if (!countryDao.getCountryByName(countryName).isPresent())
                throw new RuntimeException("COUNTRY;COUNTRY IS NOT PRESENT");

            Shop shop = Shop
                    .builder()
                    .name(name)
                    .country(countryDao.getCountryByName(countryName).get())
                    .build();

            constraintViolations = validator.validate(shop);
            if (constraintViolations.size() > 0)
                for (ConstraintViolation<?> violation : constraintViolations)
                    errorsDao.add(Errors.builder().message(violation.getMessage()).date(LocalDate.now()).build());
            else
                shopDao.add(shop);
        } catch (RuntimeException r) {
            errorsDao.add(Errors.builder().message(r.getMessage()).date(LocalDate.now()).build());
        }
    }

    public void addProducer(String producerName, String countryName, String tradeName) {
        try {
            Set<ConstraintViolation<Producer>> constraintViolations;
            if (!countryDao.getCountryByName(countryName).isPresent())
                throw new RuntimeException("COUNTRY;COUNTRY IS NOT PRESENT");
            if (!tradeDao.getTradeByName(tradeName).isPresent())
                throw new RuntimeException("TRADE;TRADE IS NOT PRESENT");

            Producer producer = Producer
                    .builder()
                    .name(producerName)
                    .country(countryDao.getCountryByName(countryName).get())
                    .trade(tradeDao.getTradeByName(tradeName).get())
                    .build();

            constraintViolations = validator.validate(producer);
            if (constraintViolations.size() > 0)
                for (ConstraintViolation<?> violation : constraintViolations)
                    errorsDao.add(Errors.builder().message(violation.getMessage()).date(LocalDate.now()).build());
            else
                producerDao.add(producer);
        } catch (RuntimeException r) {
            errorsDao.add(Errors.builder().message(r.getMessage()).date(LocalDate.now()).build());
        }
    }

    public void addProduct(String productName, String categoryName, Double price, String producerName, String producerCountry, EGuarantee... guarantees) {
        try {
            Set<ConstraintViolation<Product>> constraintViolations;
            if (!countryDao.getCountryByName(producerCountry).isPresent())
                throw new RuntimeException("COUNTRY;COUNTRY IS NOT PRESENT");
            if (!categoryDao.getCategotyByName(categoryName).isPresent())
                throw new RuntimeException("CATEGORY;CATEGORY IS NOT PRESENT");
            if (!producerDao.getProducerByNameAndCountry(producerName, producerCountry).isPresent())
                throw new RuntimeException("PRODUCER;PRODUCER IS NOT PRESENT");

            Product product = Product
                    .builder()
                    .name(productName)
                    .price(price)
                    .producer(producerDao.getProducerByNameAndCountry(producerName, producerCountry).get())
                    .category(categoryDao.getCategotyByName(categoryName).get())
                    .eGuaranteeSet(Arrays.stream(guarantees).collect(Collectors.toSet()))
                    .build();
            constraintViolations = validator.validate(product);
            if (constraintViolations.size() > 0)
                for (ConstraintViolation<?> violation : constraintViolations)
                    errorsDao.add(Errors.builder().message(violation.getMessage()).date(LocalDate.now()).build());
            else
                productDao.add(product);
        } catch (RuntimeException r) {
            errorsDao.add(Errors.builder().message(r.getMessage()).date(LocalDate.now()).build());
        }
    }

    public void addProductToStock(String productName, String categoryName, String shopName, Integer quantity) {
        try {
            Set<ConstraintViolation<Stock>> constraintViolations;
            if (!categoryDao.getCategotyByName(categoryName).isPresent())
                throw new RuntimeException("CATEGORY;CATEGORY IS NOT PRESENT");
            if (!shopDao.getShopByName(shopName).isPresent())
                throw new RuntimeException("SHOP;SHOP IS NOT PRESENT");
            if (!productDao.getProductByName(productName).isPresent())
                throw new RuntimeException("PRODUCT;PRODUCT IS NOT PRESENT");

            Stock stock = Stock
                    .builder()
                    .quantity(quantity)
                    .product(productDao.getProductByName(productName).get())
                    .shop(shopDao.getShopByName(shopName).get())
                    .build();
            constraintViolations = validator.validate(stock);
            if (constraintViolations.size() > 0)
                for (ConstraintViolation<?> violation : constraintViolations)
                    errorsDao.add(Errors.builder().message(violation.getMessage()).date(LocalDate.now()).build());
            else
                stockDao.add(stock);
        } catch (RuntimeException r) {
            errorsDao.add(Errors.builder().message(r.getMessage()).date(LocalDate.now()).build());
        }
    }

    public void addOrder(String customerName, String customerSurname, String customerCountry, String productName, String productCategory, Integer quantity, LocalDate date, Double discount, EPayment payment) {
        try {
            Set<ConstraintViolation<CustomerOrder>> constraintViolations;
            if (!customerDao.getCustomerByNameSurnameCountry(customerName, customerSurname, customerCountry).isPresent())
               throw new RuntimeException("CUSTOMER;CUSTOMER IS NOT PRESENT");
            if (!productDao.getProductByName(productName).isPresent())
                throw new RuntimeException("PRODUCT;PRODUCT IS NOT PRESENT");
            CustomerOrder customerOrder = CustomerOrder
                    .builder()
                    .customer(customerDao.getCustomerByNameSurnameCountry(customerName, customerSurname, customerCountry).get())
                    .date(date)
                    .discount(discount)
                    .payment(paymentDao.getById(1L).get())
                    .quantity(quantity)
                    .product(productDao.getProductByName(productName).get())
                    .build();
            constraintViolations = validator.validate(customerOrder);
            if (constraintViolations.size() > 0)
                for (ConstraintViolation<?> violation : constraintViolations)
                    errorsDao.add(Errors.builder().message(violation.getMessage()).date(LocalDate.now()).build());
            else
                customerOrderDao.add(customerOrder);
        } catch (RuntimeException r) {
            errorsDao.add(Errors.builder().message(r.getMessage()).date(LocalDate.now()).build());
        }
    }
}

