package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.OperationDTO;
import com.paymybuddy.paymybuddy.dto.TransferRequest;
import com.paymybuddy.paymybuddy.dto.UserDTO;
import com.paymybuddy.paymybuddy.service.ContactService;
import com.paymybuddy.paymybuddy.service.TransferService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/paymybuddy")
public class ApiController {

    private final UserService userService;

    private final TransferService transferService;

    private final ContactService contactService;

    public ApiController(UserService userService, TransferService transferService, ContactService contactService) {
        this.userService = userService;
        this.transferService = transferService;
        this.contactService = contactService;
    }

    @PutMapping(value = "/transferMoneyToBank", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserDTO transferMoneyToBank(@RequestBody TransferRequest transferRequest ,Authentication authentication) {
        return transferService.transferMoneyToBank(transferRequest, authentication);
    }

    @PutMapping(value = "/transferMoneyFromBank", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserDTO transferMoneyFromBank(@RequestBody TransferRequest transferRequest, Authentication authentication) {
        return transferService.transferMoneyFromBank(transferRequest, authentication);
    }

    @PutMapping(value = "/transferMoneyToUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserDTO transferMoneyToUser(@RequestBody TransferRequest transferRequest, Authentication authentication) {
        return transferService.transferMoneyToUser(transferRequest, authentication);
    }

    @PostMapping("/addContact")
    private void addContact(@RequestParam String contactEmail, Authentication authentication) {
        contactService.addContact(contactEmail, authentication);
    }

    @GetMapping(value = "/informations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserDTO getPersonalInformation(Authentication authentication) {
        return userService.getPersonalInformation(authentication);
    }

    @GetMapping(value = "/operations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private List<OperationDTO> getOperations(@RequestParam(required = false) Integer limit, Authentication authentication) {
        return userService.getOperations(limit, authentication);
    }
}
