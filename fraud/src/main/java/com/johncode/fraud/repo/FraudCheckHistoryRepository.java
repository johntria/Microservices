package com.johncode.fraud.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.johncode.fraud.model.FraudCheckHistory;
@Repository
public interface FraudCheckHistoryRepository extends JpaRepository<FraudCheckHistory,Integer> {
}