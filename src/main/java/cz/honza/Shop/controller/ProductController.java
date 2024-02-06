package cz.honza.Shop.controller;

import cz.honza.Shop.db.service.api.ProductService;
import cz.honza.Shop.db.service.api.request.UpdateProductRequest;
import cz.honza.Shop.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Product product) { // JSON body will be mapped to the product
        Integer id = productService.add(product);
        if (id != null) {
            System.out.println("GET /product/" + id + " called, returned name: " + product.getName());
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        System.out.println("POST /product/ persist to DB failed!");
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Product product = productService.get(id);
        if (product != null) {
            System.out.println("GET /product/" + id + " called, returned name: " + product.getName());
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        System.out.println("GET /product/" + id + " NOT found!");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);    //404
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Product> productList = productService.getProducts();
        System.out.println("GET /product returned elements of productList: " + productList.size());
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    /**
     * Update PUT nebo PATCH
     * PUT v případě, změny UPDATu celého záznamu 1 row tj. všech políček record v DB.
     * PATCH v případě změny pouze část jednotlivých atributů tj. části záznamů
     *
     * @return ResponseEntity
     */
    @PatchMapping("{id}")       //  v requeste ocekavame json
    public ResponseEntity updateProduct(@PathVariable("id") int id, @RequestBody UpdateProductRequest request) { //do @PathVariable("id") uloží id
        //meli bychom zkontrolovat jeslti je id v db, tj. existuje takovy produkt
        if (productService.get(id) != null) {
            productService.update(id, request);
            return ResponseEntity.ok().build(); // rácení HTTP odpovědi s prázdným tělem a stavovým kódem 200 OK. Je vhodný pro situace, kdy je požadavek úspěšně zpracován a není potřeba poskytnout žádný další obsah v odpovědi.
        } else {
            //  Neočekáváme nazpět resource, ale doufáme v nález id
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED) // 412 PRECONDITION_FAILED, nebyl splnen predpoklad
                    .body("Product with id: " + id + " does NOT exist!");  //log to body
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") int id) { //@RequestBody neni potreba
        if (productService.get(id) != null) {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED) // 412 PRECONDITION_FAILED, pozor ono to ani neexistuje dle id
                    .body("Product with id: " + id + " does not exist!");
        }
    }
}
