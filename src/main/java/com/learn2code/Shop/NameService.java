package com.learn2code.Shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service        //vytvori Beanu za nas, musime mit v application.properties jak java tak test! napr: name = Honza
public class NameService {
    @Value("${name}")
    private String name;

    public NameService() {
    }

    /**
     * 2024-01-30 13:12:40.123  INFO 8688 --- [           main] com.learn2code.Shop.DBInsertTests        : No active profile set, falling back to default profiles: default
     * HonzaTest
     * 2024-01-30 13:12:40.942  INFO 8688 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
     */
    @PostConstruct      //zavola sayMyName() po startu springbootu!
    private void sayMyName() {
        System.out.println(name);
    }
}
