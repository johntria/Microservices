package com.johncode.customer.controller;

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
import com.johncode.customer.service.CustomerService;

/**
 * Unit test for {@link CustomerController}
 */
@ExtendWith(MockitoExtension.class)
class TestCustomerController {
	@Mock
	CustomerService customerService;
	@InjectMocks
	CustomerController customerController;

	@Test
	void registerCustomer() {
		CustomerRegistrationRequest request = new CustomerRegistrationRequest(
				"firstname",
				"name",
				"mymail@google.gr"
		);
		Customer expectedCustomer = new Customer(1, "firstname", "lastname", "mymail@google.gr");
		when(customerService.registerCustomer(request)).thenReturn(expectedCustomer);
		Customer registeredCustomer = customerController.registerCustomer(request);

		assertEquals(registeredCustomer.getId(), expectedCustomer.getId());
		assertEquals(registeredCustomer.getFirstName(), expectedCustomer.getFirstName());
		assertEquals(registeredCustomer.getLastName(), expectedCustomer.getLastName());
		assertEquals(registeredCustomer.getEmail(), expectedCustomer.getEmail());

		verify(customerService, times(1)).registerCustomer(any());
	}
}