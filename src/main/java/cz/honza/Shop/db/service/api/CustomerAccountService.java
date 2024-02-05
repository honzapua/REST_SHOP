package cz.honza.Shop.db.service.api;

import cz.honza.Shop.domain.CustomerAccount;
import org.springframework.lang.Nullable;

public interface CustomerAccountService {
    void addCustomerAccount(CustomerAccount customerAccount);

    @Nullable   // Muze vratit null, zadne penize
    Double getMoney(int customerId);

    void setMoney(int CustomerId, double money);
}
