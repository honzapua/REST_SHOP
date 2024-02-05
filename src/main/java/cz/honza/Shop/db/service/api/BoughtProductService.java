package cz.honza.Shop.db.service.api;

import cz.honza.Shop.domain.BoughtProduct;

import java.util.List;

public interface BoughtProductService {
    void add(BoughtProduct boughtProduct);
    List<BoughtProduct> getAllbyCustomerId(int customerId); //This will be used BoughtproductController
}
