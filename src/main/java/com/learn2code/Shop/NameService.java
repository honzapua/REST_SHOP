package com.learn2code.Shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service        //vytvori Beanu za nas
public class NameService {
    @Value("${name}")
    private String name;

    public NameService() {
    }

    @PostConstruct      //zavola sayMyName() po startu springbootu!
    private void sayMyName() {
        System.out.println(name);
    }
}
