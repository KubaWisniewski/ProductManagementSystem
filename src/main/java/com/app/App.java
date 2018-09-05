package com.app;

import com.app.model.Country;
import com.app.model.Customer;
import com.app.model.EGuarantee;
import com.app.model.EPayment;
import com.app.repository.CountryDao;
import com.app.repository.CountryDaoImpl;
import com.app.repository.CustomerDao;
import com.app.repository.CustomerDaoImpl;
import com.app.service.MyService;

import java.time.LocalDate;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MyService ms= new MyService();
        CustomerDao customerDao =new CustomerDaoImpl();
        CountryDao countryDao= new CountryDaoImpl();
       // countryDao.add(Country.builder().name("USA").build());
       // ms.addCustomer("JAN","NOWAK",20,"USA");
        //ms.addShop("AAA","USA");
       // ms.addProducer("ADIDAS","USA","CLOTHES");
       // ms.addProduct("AIR MAX","SHOES",250.0,"ADIDAS","USA", EGuarantee.HELP_DESK,EGuarantee.MONEY_BACK);
        //ms.addProductToStock("AIR MAX","SHOES","AAA",20);
        //System.out.println(customerDao.getAll().size());
        ms.addOrder("ADAM","NOWAK","USA","AIR MAX","SHOES",10, LocalDate.now(),0.5, EPayment.CASH);
    }
}
