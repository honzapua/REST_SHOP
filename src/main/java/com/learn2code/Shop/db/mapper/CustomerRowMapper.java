package com.learn2code.Shop.db.mapper;

import com.learn2code.Shop.domain.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();

        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setSurname(rs.getString("surname"));
        customer.setEmail(rs.getString("email"));
        customer.setAddress(rs.getString("address"));
        // to avoid primitive int=0 age conversion. In case of Customer do not input age into DB! He can input 0 age now!
        // customer.setAge(rs.getInt("age"));
        customer.setAge(rs.getObject("age") == null ? null : rs.getInt("age"));
        // no need to advanced set because String could be null
        customer.setPhone_number(rs.getString("phone_number"));

        return customer;
    }
}
