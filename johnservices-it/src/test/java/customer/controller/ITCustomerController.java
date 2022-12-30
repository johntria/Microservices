package customer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johncode.customer.CustomerApplication;
import com.johncode.customer.model.Customer;
import com.johncode.customer.repository.CustomerRepository;
import com.johncode.customer.service.CustomerService;

/**
 * Integration Test for{@link com.johncode.customer.controller.CustomerController}
 */
@SpringBootTest(classes = {CustomerApplication.class}, properties = "spring.config.name=application-dev")
@AutoConfigureMockMvc
class ITCustomerController {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerService customerService;
	@Autowired
	MockMvc mockMvc;

	@Test
	void registerCustomer() throws Exception {
		MvcResult result = mockMvc.perform(post("/api/v1/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{" +
								"    \"firstName\": \"John\"," +
								"    \"lastName\": \"Triantafyllakis\"," +
								"    \"email\": \"john.3fyl@gmail.com\"" +
								"}")
				)
				.andExpect(content().contentType("application/json"))
				.andExpect(status().isOk())
				.andReturn();

		String jsonResponse = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		Customer customer = objectMapper.readValue(jsonResponse, Customer.class);

		assertEquals("john.3fyl@gmail.com", customer.getEmail());
		assertEquals("John", customer.getFirstName(), "John");
		assertEquals("Triantafyllakis", customer.getLastName());

		Optional<Customer> byId = customerRepository.findById(customer.getId());
		assertTrue(byId.isPresent());
		customerRepository.deleteById(byId.get().getId());
	}
}