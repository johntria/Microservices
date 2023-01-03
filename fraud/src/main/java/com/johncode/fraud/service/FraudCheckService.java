package com.johncode.fraud.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.johncode.fraud.model.FraudCheckHistory;
import com.johncode.fraud.repo.FraudCheckHistoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FraudCheckService {
	private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

	public boolean isFraudulentCustomer(Integer customerId) {
		fraudCheckHistoryRepository.save(FraudCheckHistory.builder().customerId(customerId).isFraudster(Boolean.FALSE).createdAt(LocalDateTime.now()).build());
		return false;
	}
}