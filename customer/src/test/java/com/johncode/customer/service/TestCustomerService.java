package com.johncode.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.johncode.customer.dto.CustomerRegistrationRequest;
import com.johncode.customer.dto.FraudCheckResponse;
import com.johncode.customer.model.Customer;
import com.johncode.customer.repository.CustomerRepository;

/**
 * Unit test for {@link CustomerService}
 */
@ExtendWith(MockitoExtension.class)
class TestCustomerService {
	@Mock
	CustomerRepository customerRepository;
	@Mock
	RestTemplate restTemplate;
	@InjectMocks
	CustomerService customerService;

	@Test
	void registerCustomer() {
		//given
		CustomerRegistrationRequest request = new CustomerRegistrationRequest("firstname", "name", "mymail@google.gr");
		Customer expectedCustomer = new Customer(1, "firstname", "lastname", "mymail@google.gr");
		//when
		when(customerRepository.save(any())).thenReturn(expectedCustomer);
		when(restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
				FraudCheckResponse.class, 1)).thenReturn(new FraudCheckResponse(Boolean.FALSE));
		Customer customer = customerService.registerCustomer(request);
		//then
		assertEquals(customer.getId(), expectedCustomer.getId());
		assertEquals(customer.getFirstName(), expectedCustomer.getFirstName());
		assertEquals(customer.getLastName(), expectedCustomer.getLastName());
		assertEquals(customer.getEmail(), expectedCustomer.getEmail());
		//verify
		verify(customerRepository, times(1)).save(any());
	}

	@Test
	void registerCustomer_expected_exception() {

		//given
		CustomerRegistrationRequest request = new CustomerRegistrationRequest("firstname", "name", "mymail@google.gr");
		Customer expectedCustomer = new Customer(1, "firstname", "lastname", "mymail@google.gr");
		//when
		when(customerRepository.save(any())).thenReturn(expectedCustomer);
		when(restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
				FraudCheckResponse.class, 1)).thenReturn(new FraudCheckResponse(Boolean.TRUE));
		Customer customer =null;
		Exception expectedException = assertThrows(IllegalStateException.class, () -> {
			 customerService.registerCustomer(request);

		});
		//verify
		verify(customerRepository, times(1)).save(any());
		verify(restTemplate, times(1)).getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
				FraudCheckResponse.class, 1);
		assertEquals("You are Fraudster!",expectedException.getMessage());
	}
}