package com.johncode.fraud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johncode.fraud.dto.FraudCheckResponse;
import com.johncode.fraud.service.FraudCheckService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
public class FraudController {
	private FraudCheckService fraudCheckService;

	@GetMapping(path="{customerId}")
	public FraudCheckResponse isFraudster(@PathVariable("customerId")Integer customerID){
		boolean fraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerID);
		return new FraudCheckResponse(fraudulentCustomer);
	}
}