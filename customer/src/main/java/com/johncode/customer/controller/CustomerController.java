package com.johncode.customer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johncode.customer.dto.CustomerRegistrationRequest;
import com.johncode.customer.model.Customer;
import com.johncode.customer.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public record CustomerController(CustomerService customerService) {
	@PostMapping()
	public Customer registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
		log.info("New customer registration {}", customerRegistrationRequest);
		return customerService.registerCustomer(customerRegistrationRequest);
	}

}