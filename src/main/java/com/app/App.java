package com.app;


import com.app.model.EGuarantee;
import com.app.service.MyService;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        MyService ms = new MyService();
        //ms.getInformationAboutProductsWithMaxPriceInCategory();
        //ms.getInformationAboutProductsWithCustomers("POLAND", 25, 30);
        //ms.getInformationAboutProductsWithGuarantee(EGuarantee.HELP_DESK);
        //ms.getInformationAboutShops();
        //ms.getInformationAboutProducersWithTradeAndQuantity("CLOTHES",25);
        //ms.getInformationAboutProductsWithCustomerNameSurnameAndCountry("JAN","KOWALSKI","POLAND");
        //ms.getInformationAboutOrdersByPriceAndBetweenDates(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 4, 1), 2000.0);
        //ms.getInformationAboutCustomersWithTheSameCountry();
    }
}
