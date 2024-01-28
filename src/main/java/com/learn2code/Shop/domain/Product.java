package com.learn2code.Shop.domain;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

public class Product {
    @Nullable   //Id bude vytvaret DB
    private Integer id;
    @NonNull
    private int merchantId; //pozor zde int! pri tvorbe produktu uz vime merchant_id
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private double price;   //double
    @NonNull
    private Timestamp created_at;   //java sql
    @NonNull
    private int available;

    public Product() {
    }
// bez id a createdAt v parametru konstruktoru!
    public Product(int merchantId, @NonNull String name, @NonNull String description, double price, int available) {
        this.merchantId = merchantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.created_at = Timestamp.from(Instant.now()); // v case vytvoreni konstruktoru
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NonNull
    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(@NonNull Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return merchantId == product.merchantId && Double.compare(price, product.price) == 0 && available == product.available && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(created_at, product.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, merchantId, name, description, price, created_at, available);
    }
}
