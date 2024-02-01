package cz.honza.Shop.db.service.api;

import cz.honza.Shop.domain.Customer;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();// melo by se jmenovat Customers, vraci list!
    @Nullable   //muze vratit null
    Customer get(int id);
    @Nullable   //nemusi se insert podarit, nekdy null
    Integer add(Customer customer);     // returns generated ID from db


}
