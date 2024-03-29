package cz.honza.Shop.domain;

import java.util.Objects;

public class CustomerAccount {
    // nebude mit vlastni id, bude navazan na id customera
    private int customerId;
    private double money;

    public CustomerAccount() {
    }

    public CustomerAccount(int customerId, double money) {
        this.customerId = customerId;
        this.money = money;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAccount that = (CustomerAccount) o;
        return customerId == that.customerId && Double.compare(money, that.money) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, money);
    }
}
