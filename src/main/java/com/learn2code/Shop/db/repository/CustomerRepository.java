package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.db.mapper.CustomerRowMapper;
import com.learn2code.Shop.domain.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component  //kvuli pouziti v service
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Customer get(int id) {
        final String sql = "SELECT * FROM customer WHERE customer.id = " + id;
        try {
            //one Object we need only 1 where id =  !
            return jdbcTemplate.queryForObject(sql, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {    // if id does not return
            return null;
        }
    }

    public Integer add(Customer customer) {
        final String sql = "INSERT INTO customer(name, surname, email, address, age, phone_number) VALUES (?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); // for RETURNING Generated key from DB thx to update
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // if Customer is set return generated id
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSurname());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                if (customer.getAge() != null) {
                    ps.setInt(5, customer.getAge());
                } else {
                    ps.setNull(5, Types.INTEGER);
                }
                ps.setString(6, customer.getPhone_number());
                return ps;
            }
        }, keyHolder);

        //zpusob ziskani id po vygenerovani radku tabulky
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }
    // vsichni zakaznici .query, 1 .queryForObjet
    public List<Customer> getAll() {
        final String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

}
