package sg.edu.nus.iss.day24_lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import sg.edu.nus.iss.day24_lecture.model.BankAccount;
import sg.edu.nus.iss.day24_lecture.service.BankAccountService;

@RestController
@RequestMapping("/api/bankaccounts")
public class BankAccountController {
    
    @Autowired
    BankAccountService service;

    @PostMapping
    public ResponseEntity<Boolean> createAccount(@RequestBody BankAccount bankAccount) {
        Boolean accountCreated = service.createAccount(bankAccount);

        if (accountCreated) {
            return ResponseEntity.ok().body(accountCreated);
        } else {
            return ResponseEntity.internalServerError().body(accountCreated);
        }
    }

    @GetMapping("/{account-id}")
    public ResponseEntity<BankAccount> getAccountByID(@PathVariable("account-id") Integer id) {
        BankAccount account = service.retrieveAccountByID(id);

        return new ResponseEntity<BankAccount>(account, HttpStatus.OK);

    }

    @PostMapping("/transfer/{transferer-id}/receive/{receiver-id}/amount/{amount}")
    public ResponseEntity<Boolean> transferMoney(@PathVariable("transferer-id") int transfererID, @PathVariable("receiver-id") int receiverID, @PathVariable("amount") float amount) {
        service.transferMoney(transfererID, receiverID, amount);

        return new ResponseEntity<Boolean>((service.transferMoney(transfererID, receiverID, amount)), HttpStatus.OK);
    }
    
}
