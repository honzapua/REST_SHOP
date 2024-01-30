package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.db.service.request.UpdateProductRequest;
import com.learn2code.Shop.domain.Product;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    @Nullable
    Product get(int id);

    @Nullable
    Integer add(Product product);   //return generated id from DB

    void delete(int id);    // delete by id from DB

    /**
     * void update(int id, String name, double price, int available); we
     * UpdateProductRequest.class solve this separately, so we use it as a parameter
     */
    void update(int id, UpdateProductRequest request);  // if new field be there DB will be updated!
}
