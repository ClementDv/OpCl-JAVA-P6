package com.paymybuddy.paymybuddy.exception;

import com.paymybuddy.paymybuddy.model.ErrorResponse;
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

    @ExceptionHandler(NoEnoughMoneyOnBalanceException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoEnoughMoneyOnBalanceException(NoEnoughMoneyOnBalanceException e) {
        logger.error("Not enough money on balance");
        return response(new ErrorResponse(400, "NOT_ENOUGH_MONEY", e.getMessage())
                .withMetadata("balance", e.getBalance()).withMetadata("transferAmount", e.getAmount()));
    }

    @ExceptionHandler(NonValidAmountException.class)
    @ResponseBody
    public ResponseEntity<?> handleNonValidAmountException(NonValidAmountException e) {
        logger.error("Non valid amount");
        return response(new ErrorResponse(400, "NON_VALID_AMOUNT", e.getMessage())
                .withMetadata("amount", e.getAmount()));
    }

    @ExceptionHandler(NoUserFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoUserFoundException(NoUserFoundException e) {
        logger.error("No user found");
        return response(new ErrorResponse(404, "NO_USER_FOUND", e.getMessage())
                .withMetadata("email", e.getEmail()));
    }

    @ExceptionHandler(NonValidEmailLogin.class)
    @ResponseBody
    public ResponseEntity<?> handleNonValidEmailLogin(NonValidEmailLogin e) {
        logger.error("Email not valid");
        return response(new ErrorResponse(400, "NON_VALID_EMAIL", e.getMessage())
                .withMetadata("email", e.getEmail()));
    }


    protected ResponseEntity<ErrorResponse> response(ErrorResponse errorResponse) {
        HttpStatus status = HttpStatus.resolve(errorResponse.getStatus());
        logger.debug("Respoding with a stats of {}", status);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }
}
