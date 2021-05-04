package com.dev.java.atmDispenserDemo.service;

import com.dev.java.atmDispenserDemo.controller.AtmDispenController;
import com.dev.java.atmDispenserDemo.exception.InsufficientBalanceException;
import com.dev.java.atmDispenserDemo.exception.InsufficientNoteException;
import com.dev.java.atmDispenserDemo.exception.InvalidAmountException;
import com.dev.java.atmDispenserDemo.model.ResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Prajula
 */

@RestController
@RequestMapping(value = "/rest/atm")
public class AtmDispService {

    @Autowired
    AtmDispenController atmDispenController;

    @GetMapping("/dispense")
    public ResponseEntity<ResponseWrapper> deposit(
            @RequestParam("amount") int amount)
            throws InsufficientNoteException, InvalidAmountException, InsufficientBalanceException {
        return atmDispenController.calculateBank(amount);
    }

    @GetMapping("/checkBalance")
    public ResponseEntity<ResponseWrapper> checkBalance() {
        return atmDispenController.checkBalance();
    }

}
