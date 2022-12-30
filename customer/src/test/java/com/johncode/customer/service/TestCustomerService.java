package com.johncode.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.johncode.customer.dto.CustomerRegistrationRequest;
import com.johncode.customer.model.Customer;
import com.johncode.customer.repository.CustomerRepository;

/**
 * Unit test for {@link CustomerService}
 */
@ExtendWith(MockitoExtension.class)
class TestCustomerService {
	@Mock
	CustomerRepository customerRepository;
	@InjectMocks
	CustomerService customerService;

	@Test
	void registerCustomer() {
		CustomerRegistrationRequest request = new CustomerRegistrationRequest(
				"firstname",
				"name",
				"mymail@google.gr"
		);
		Customer expectedCustomer = new Customer(1, "firstname", "lastname", "mymail@google.gr");
		when(customerRepository.save(any())).thenReturn(expectedCustomer);
		Customer customer = customerService.registerCustomer(request);

		assertEquals(customer.getId(), expectedCustomer.getId());
		assertEquals(customer.getFirstName(), expectedCustomer.getFirstName());
		assertEquals(customer.getLastName(), expectedCustomer.getLastName());
		assertEquals(customer.getEmail(), expectedCustomer.getEmail());

		verify(customerRepository, times(1)).save(any());
	}
}