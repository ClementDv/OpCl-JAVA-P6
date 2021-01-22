package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.data.TestData;
import com.paymybuddy.paymybuddy.model.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findByEmailReceiverOrEmitterWithLimitOrderByDateTest(){
        testEntityManager.persist(new Operation("USR_clement@ocr.fr", "BNK_BNP", 200.00));
        testEntityManager.persist(new Operation("BNK_BNP", "USR_clement@ocr.fr", 300.00));
        testEntityManager.flush();
        assertThat(operationRepository.findByEmailReceiverOrEmitterWithLimitOrderByDate("USR_clement@ocr.fr", PageRequest.of(0, 10)))
                .isEqualTo(TestData.getOperationListRepositoryTest());
    }

}
