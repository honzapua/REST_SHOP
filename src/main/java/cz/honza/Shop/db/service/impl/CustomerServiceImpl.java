package cz.honza.Shop.db.service.impl;

import cz.honza.Shop.db.repository.CustomerRepository;
import cz.honza.Shop.db.service.api.CustomerService;
import cz.honza.Shop.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service    // and @Component help to create object during initialization we will use it later in Controller
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    // constructor injection funguje, kdyz jsme dali anotaci @Component CustomerRepository bean
    public CustomerServiceImpl(CustomerRepository customerRepository) { //constructor injection
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.getAll();
    }

    @Override
    public Customer get(int id) {
        return customerRepository.get(id);
    }

    @Override
    public Integer add(Customer customer) {
        return customerRepository.add(customer);
    }
}
