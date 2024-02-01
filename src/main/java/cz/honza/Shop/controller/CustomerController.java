package cz.honza.Shop.controller;

import cz.honza.Shop.db.service.api.CustomerService;
import cz.honza.Shop.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")     //nemusi jednotlive metody zacinat customer, ale nastavim zacatek pro celou tridu
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {    //constructor injection customerService
        this.customerService = customerService;
    }
    @PostMapping
    public ResponseEntity add(@RequestBody Customer customer) { //v
        Integer id = customerService.add(customer); // v pripade uspesneho vlozeni ziskame id
        if (id != null) {       // kdyz se povede vlozit
            return new ResponseEntity<>(id, HttpStatus.CREATED);  //201
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);    //500
    }

    @GetMapping("{id}") //tohle bude za customer
    public ResponseEntity get(@PathVariable("id") int id) { //vytahne parametr @GetMapping("{id}") do promenne int id
        Customer customer = customerService.get(id);
        if (customer == null) {     //kdyz nenajdeme customera podle id
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);    //404
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);   //200
    }
    @GetMapping
    public ResponseEntity getAll() {
        List<Customer> customerList = customerService.getCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }
}
