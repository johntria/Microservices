package com.johncode.customer.service;

import org.springframework.stereotype.Service;

import com.johncode.customer.dto.CustomerRegistrationRequest;
import com.johncode.customer.model.Customer;
import com.johncode.customer.repository.CustomerRepository;

@Service
public record CustomerService(CustomerRepository customerRepository) {
	public Customer registerCustomer(CustomerRegistrationRequest request) {
		Customer customer = Customer.builder()
				.firstName(request.firstName())
				.lastName(request.lastName())
				.email(request.email())
				.build();
		//todo:check if mail is valid
		//todo:check if email not taken
		return customerRepository.save(customer);
	}
}