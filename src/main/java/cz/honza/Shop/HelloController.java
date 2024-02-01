package cz.honza.Shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("sayHello")     //http://localhost:8080/sayHello
    public String helloWorld() {
        return "Hello World";
    }
}
