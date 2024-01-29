package com.learn2code.Shop;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Test {
    private JdbcTemplate jdbcTemplate;

    public Test(JdbcTemplate jdbcTemplate) {    //DI pomoci konstruktoru
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.execute("select * from customer");//ZADNY vypis, ale zadna exception
    }
}
