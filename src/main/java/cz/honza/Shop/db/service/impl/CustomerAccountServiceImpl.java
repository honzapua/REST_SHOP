package cz.honza.Shop.db.service.impl;

import cz.honza.Shop.db.repository.CustomerAccountRepository;
import cz.honza.Shop.db.service.api.CustomerAccountService;
import cz.honza.Shop.domain.CustomerAccount;
import org.springframework.stereotype.Service;

@Service    // vytvori beanu
public class CustomerAccountServiceImpl implements CustomerAccountService {
    private final CustomerAccountRepository customerAccountRepository;

    public CustomerAccountServiceImpl(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public void addCustomerAccount(CustomerAccount customerAccount) {
        customerAccountRepository.add(customerAccount);
    }

    @Override
    public Double getMoney(int customerId) {    // nevolame RESTem, ale v ShoppingService
        return customerAccountRepository.getMoney(customerId);
    }

    @Override
    public void setMoney(int CustomerId, double money) {    // nevolame RESTem, ale v ShoppingService
        customerAccountRepository.setMoney(CustomerId, money);
    }
}
