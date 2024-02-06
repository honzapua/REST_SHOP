package cz.honza.Shop.db.service.api;

import cz.honza.Shop.domain.CustomerAccount;
import org.springframework.lang.Nullable;

public interface CustomerAccountService {
    void addCustomerAccount(CustomerAccount customerAccount);

    @Nullable   // can return null, NO money on account
    Double getMoney(int customerId);

    void setMoney(int CustomerId, double money);
}
