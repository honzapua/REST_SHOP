package com.learn2code.Shop.domain;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class Customer {
    @Nullable
    // Bude za nas vytvaret DB autoincrement. V case vytvareni instance nevime ID, budeme vedet az po ulozeni do DB a nacteni zpet!
    private Integer id;
    @NonNull    //povinne, NEsmi byt null
    private String name;
    @NonNull    //povinne, NEsmi byt null
    private String surname;
    @NonNull    //povinne, NEsmi byt null
    private String email;
    @NonNull    //povinne, NEsmi byt null
    private String address;

    @Nullable
    private Integer age;    // nepovinne, muze byt null   @Nullable
    @Nullable
    private String phone_number;    //  nepovinne, muze byt null      @Nullable

    public Customer() {
    }

    //Customer konstruktor je vygenerovan bez ID fieldu
    public Customer(@NonNull String name, @NonNull String surname, @NonNull String email, @NonNull String address, @Nullable Integer age, @Nullable String phone_number) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.age = age;
        this.phone_number = phone_number;
    }
    // Gettery a settery generovany i s ID fieldem
    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getSurname() {
        return surname;
    }

    public void setSurname(@NonNull String surname) {
        this.surname = surname;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @Nullable
    public Integer getAge() {
        return age;
    }

    public void setAge(@Nullable Integer age) {
        this.age = age;
    }

    @Nullable
    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(@Nullable String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(surname, customer.surname) && Objects.equals(email, customer.email) && Objects.equals(address, customer.address) && Objects.equals(age, customer.age) && Objects.equals(phone_number, customer.phone_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, address, age, phone_number);
    }
}
