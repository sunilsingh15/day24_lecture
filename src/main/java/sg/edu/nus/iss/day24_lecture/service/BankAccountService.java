package sg.edu.nus.iss.day24_lecture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day24_lecture.model.BankAccount;
import sg.edu.nus.iss.day24_lecture.repository.BankAccountRepo;

@Service
public class BankAccountService {

    @Autowired
    BankAccountRepo repository;

    public BankAccount retrieveAccountByID(int id) {
        return repository.getAccountByID(id);
    }

    public Boolean createAccount(BankAccount bankAccount) {
        return repository.createAccount(bankAccount);
    }
    
}
