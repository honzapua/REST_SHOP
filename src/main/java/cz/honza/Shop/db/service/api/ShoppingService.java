package cz.honza.Shop.db.service.api;

import cz.honza.Shop.db.service.api.request.BuyProductRequest;
import cz.honza.Shop.db.service.api.response.BuyProductResponse;

public interface ShoppingService {
    BuyProductResponse buyProduct(BuyProductRequest request);
}
