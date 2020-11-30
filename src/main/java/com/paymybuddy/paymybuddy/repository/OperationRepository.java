package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
}
