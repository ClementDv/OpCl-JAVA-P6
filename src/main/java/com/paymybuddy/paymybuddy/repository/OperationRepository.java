package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("select o from Operation o where (o.emitterUserId.id = :id or o.receiverUserId.id = :id) order by o.at desc")
    List<Operation> findByEmailReceiverOrEmitterWithLimitOrderByDate(Long id, Pageable pageable);
}
