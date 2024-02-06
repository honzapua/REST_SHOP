package cz.honza.Shop.db.repository;

import cz.honza.Shop.domain.CustomerAccount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CustomerAccountRepository {
    private final JdbcTemplate jdbcTemplate;
    public CustomerAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void add(CustomerAccount customerAccount) {  // do NOT return int this time!
        final String sql = "INSERT INTO customer_account (customer_id, money) VALUES (?, ?)";
        jdbcTemplate.update(sql, customerAccount.getCustomerId(), customerAccount.getMoney());
    }
    @Nullable   // could be no money
    public Double getMoney(int customerId) {    // Return only number in Double object
        final String sql = "SELECT money FROM customer_account WHERE customer_id = " + customerId;
        try {
            return jdbcTemplate.queryForObject(sql, Double.class); // search for Double object not customerRowMapper
        } catch (DataAccessException e) {    // if id does not return
            return null;
        }
    }
    public void setMoney(int customerId, double money) {
        final String sql = "UPDATE customer_account SET money = ?  WHERE customer_id = ?";
        jdbcTemplate.update(sql, money, customerId);
    }
}
