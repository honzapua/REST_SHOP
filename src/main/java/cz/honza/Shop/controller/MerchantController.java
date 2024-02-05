package cz.honza.Shop.controller;

import cz.honza.Shop.db.service.api.MerchantService;
import cz.honza.Shop.domain.Merchant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("merchant")
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {    // constructor injetion
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Merchant merchant) {
        Integer id = merchantService.add(merchant);
        if (id != null) {
            System.out.println("POST /merchant/ called, persisted to DB with id: " + id);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        System.out.println("POST /merchant/ persist to DB failed!" );
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);    //null pointer exception or DB failure etc.
    }

    @GetMapping("{id}") //not working without {} around id
    public ResponseEntity get(@PathVariable("id") int id) {
        Merchant merchant = merchantService.get(id);
        if (merchant != null) {
            System.out.println("GET /merchant/" + id + " called, returned name: " + merchant.getName());
            return new ResponseEntity<>(merchant, HttpStatus.OK);   //200
        }
        System.out.println("GET /merchant/" + id + " NOT found!");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);    //404
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Merchant> merchantList = merchantService.getMerchants();
        System.out.println("GET /merchant returned elements of merchantList: " + merchantList.size());
        return new ResponseEntity<>(merchantList, HttpStatus.OK);
    }
}
