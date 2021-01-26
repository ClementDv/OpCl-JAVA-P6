package com.paymybuddy.paymybuddy.model;

import com.paymybuddy.paymybuddy.dto.OperationDTO;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.security.model.request.JwtRequest;
import com.paymybuddy.paymybuddy.security.model.response.JwtResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@ExtendWith(SpringExtension.class)
public class ModelTest {

    @Test
    void UserPOJOTest() {
        final Class<?> userClass = User.class;

        assertPojoMethodsFor(userClass)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.TO_STRING);
    }

    @Test
    void BankPOJOTest() {
        final Class<?> bankClass = Bank.class;

        assertPojoMethodsFor(bankClass)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.TO_STRING);
    }

    @Test
    void ContactPOJOTest() {
        final Class<?> contactClass = Contact.class;

        assertPojoMethodsFor(contactClass)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.TO_STRING);
    }

    @Test
    void OperationPOJOTest() {
        final Class<?> operationClass = Operation.class;

        assertPojoMethodsFor(operationClass)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.TO_STRING);
    }

    @Test
    void OperationDTOPOJOTest() {
        final Class<?> operationDTOClass = OperationDTO.class;

        assertPojoMethodsFor(operationDTOClass)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.TO_STRING);
    }

    @Test
    void UserDTOPOJOTest() {
        final Class<?> userDTOClass = UserDTO.class;

        assertPojoMethodsFor(userDTOClass)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.TO_STRING);
    }


    @Test
    void JwtRequestPOJOTest() {
        final Class<?> jwtRequestClass = JwtRequest.class;

        assertPojoMethodsFor(jwtRequestClass)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.TO_STRING);
    }


    @Test
    void JwtResponsePOJOTest() {
        final Class<?> jwtResponseClass = JwtResponse.class;

        assertPojoMethodsFor(jwtResponseClass)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .testing(Method.EQUALS)
                .testing(Method.HASH_CODE)
                .testing(Method.TO_STRING);
    }
}
