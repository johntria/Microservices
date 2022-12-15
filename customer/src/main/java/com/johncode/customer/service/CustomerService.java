package com.johncode.customer.service;

import com.johncode.customer.dto.CustomerRegistrationRequest;
import com.johncode.customer.model.Customer;
import org.springframework.stereotype.Service;

@Service
public record CustomerService() {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo:check if mail is valid
        //todo:check if email not taken
        //todo:store customer in db;
    }
}
