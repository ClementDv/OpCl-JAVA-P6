package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.dto.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private final Logger logger = LogManager.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(ContactAlreadyAssignedException.class)
    @ResponseBody
    public ResponseEntity<?> handleContactAlreadyAssignedException(ContactAlreadyAssignedException e) {
        logger.error("Contact {} already exists for user {}", e.getContactId(), e.getUserId());
        return response(new ErrorResponse(ErrorCodesEnum.CONTACT_ALREADY_ASSIGNED.getCode(), ErrorCodesEnum.CONTACT_ALREADY_ASSIGNED.getError(), e.getMessage())
                .withMetadata("contactId", e.getContactId()).withMetadata("userId", e.getUserId()));
    }

    @ExceptionHandler(NoEnoughMoneyOnBalanceException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoEnoughMoneyOnBalanceException(NoEnoughMoneyOnBalanceException e) {
        logger.error("Not enough money on balance");
        return response(new ErrorResponse(ErrorCodesEnum.NOT_ENOUGH_MONEY.getCode(), ErrorCodesEnum.NOT_ENOUGH_MONEY.getError(), e.getMessage())
                .withMetadata("balance", e.getBalance()).withMetadata("transferAmount", e.getAmount()));
    }

    @ExceptionHandler(NonValidAmountException.class)
    @ResponseBody
    public ResponseEntity<?> handleNonValidAmountException(NonValidAmountException e) {
        logger.error("Non valid amount");
        return response(new ErrorResponse(ErrorCodesEnum.NON_VALID_AMOUNT.getCode(), ErrorCodesEnum.NON_VALID_AMOUNT.getError(),e.getMessage())
                .withMetadata("amount", e.getAmount()));
    }

    @ExceptionHandler(NoUserFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoUserFoundException(NoUserFoundException e) {
        logger.error("No user found");
        return response(new ErrorResponse(ErrorCodesEnum.NO_USER_FOUND.getCode(), ErrorCodesEnum.NO_USER_FOUND.getError(), e.getMessage())
                .withMetadata("email", e.getEmail()));
    }

    @ExceptionHandler(NonValidEmailLogin.class)
    @ResponseBody
    public ResponseEntity<?> handleNonValidEmailLogin(NonValidEmailLogin e) {
        logger.error("Email not valid");
        return response(new ErrorResponse(ErrorCodesEnum.NON_VALID_EMAIL.getCode(), ErrorCodesEnum.NON_VALID_EMAIL.getError(), e.getMessage())
                .withMetadata("email", e.getEmail()));
    }


    protected ResponseEntity<ErrorResponse> response(ErrorResponse errorResponse) {
        HttpStatus status = HttpStatus.resolve(errorResponse.getStatus());
        logger.debug("Respoding with a status of {}", status);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }
}
