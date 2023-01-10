package com.johncode.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.johncode.customer.dto.CustomerRegistrationRequest;
import com.johncode.customer.dto.FraudCheckResponse;
import com.johncode.customer.model.Customer;
import com.johncode.customer.repository.CustomerRepository;

@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {
	public Customer registerCustomer(CustomerRegistrationRequest request) {
		Customer customer = Customer.builder()
				.firstName(request.firstName())
				.lastName(request.lastName())
				.email(request.email())
				.build();
		//todo:check if mail is valid
		//todo:check if email not taken
		//todo:check if fraudster
		Customer savedCustomer = customerRepository.save(customer);
		FraudCheckResponse forObject = restTemplate.getForObject(
				"http://FRAUD/api/v1/fraud-check/{customerId}",
				FraudCheckResponse.class,
				savedCustomer.getId()
		);
		if (Boolean.TRUE.equals(forObject.isFraudster())) {
			throw new IllegalStateException("You are Fraudster!");
		}
		//todo:send notification;
		return savedCustomer;
	}
}