package com.learn2code.Shop;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service        //vytvori Beanu
public class NameService {
    @Value("${name}")
    private String name;

    public NameService(/*String name*/) {
//        System.out.println(name);
    }

    @PostConstruct
    private void sayMyName() {
        System.out.println(name);
    }
}
