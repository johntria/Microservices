package com.johncode.customer.service;

import org.springframework.stereotype.Service;


import com.johncode.clients.fraud.FraudCheckResponse;
import com.johncode.clients.fraud.FraudClient;
import com.johncode.customer.dto.CustomerRegistrationRequest;
import com.johncode.customer.model.Customer;
import com.johncode.customer.repository.CustomerRepository;

@Service
public record CustomerService(CustomerRepository customerRepository, FraudClient fraudClient) {
	public Customer registerCustomer(CustomerRegistrationRequest request) {
		Customer customer =
				Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
		//todo:check if mail is valid
		//todo:check if email not taken
		Customer savedCustomer = customerRepository.save(customer);
		FraudCheckResponse fraudster = fraudClient.isFraudster(customer.getId());
		if (Boolean.TRUE.equals(fraudster.isFraudster())) {
			throw new IllegalStateException("You are Fraudster!");
		}
		//todo:send notification;
		return savedCustomer;
	}
}