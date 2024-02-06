package cz.honza.Shop.db.service.impl;

import cz.honza.Shop.db.service.api.*;
import cz.honza.Shop.db.service.api.request.BuyProductRequest;
import cz.honza.Shop.db.service.api.response.BuyProductResponse;
import cz.honza.Shop.domain.BoughtProduct;
import cz.honza.Shop.domain.Customer;
import cz.honza.Shop.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    private final ProductService productService;
    private final CustomerService customerService;
    private final CustomerAccountService customerAccountService;
    private final BoughtProductService boughtProductService;

    public ShoppingServiceImpl(ProductService productService, CustomerService customerService, CustomerAccountService customerAccountService, BoughtProductService boughtProductService) {
        this.productService = productService;
        this.customerService = customerService;
        this.customerAccountService = customerAccountService;
        this.boughtProductService = boughtProductService;
    }

    @Override
    public BuyProductResponse buyProduct(BuyProductRequest request) {
        int productId = request.getProductId(); // variable to provent often getProductId() again
        int customerId = request.getCustomerId();

        Product product = productService.get(productId);    // could be nullable
        if (product == null) {  //first step if even exist NO need to do other steps!
            return new BuyProductResponse(false, "Product with id: " + productId + " does NOT exist!");
        }

        Customer customer = customerService.get(customerId);
        if (customer == null) {
            return new BuyProductResponse(false, "Customer with id: " + customerId + " does NOT exist!");
        }

        if (product.getAvailable() < request.getQuantity()) {
            return new BuyProductResponse(false, "Not enough products in stock!");
        }
        //if both customer & product exists we can finally buy...but we must get money from customerAccountService based on id
        Double customerMoney = customerAccountService.getMoney(customerId);
        // check if do not have money => do NOT HAVE account
        if (customerMoney == null) {
            return new BuyProductResponse(false, "Customer with id: " + customerId + " does NOT have Account!");
        } else {    // some money
            double totalPriceOfRequest = product.getPrice() * request.getQuantity();
            if (customerMoney >= totalPriceOfRequest) {
                productService.updateAvailableInternal(productId, product.getAvailable() - request.getQuantity());
                customerAccountService.setMoney(customerId,customerMoney - totalPriceOfRequest);
                boughtProductService.add(new BoughtProduct(productId, customerId, request.getQuantity()));
                return new BuyProductResponse(true);    //  Error messege do not work like in false success, "Customer with id: " + customerId + " Bought: " + request.getQuantity() + " products."
            } else {
                return new BuyProductResponse(false, "Customer with id: " + customerId + " does NOT have enough Money! ");
            }
        }
    }
}
