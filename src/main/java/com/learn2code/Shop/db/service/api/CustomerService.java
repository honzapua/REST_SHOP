package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.Customer;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomer();// melo by se jmenovat Customers, vraci list!
    @Nullable   //muze vratit null
    Customer get(int id);
    @Nullable   //nemusi se insert podarit, nekdy null
    Integer add(Customer customer);     // returns generated ID from db


}
