package sg.edu.nus.iss.day24_lecture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.day24_lecture.exception.AccountBlockedAndDisabledException;
import sg.edu.nus.iss.day24_lecture.exception.AmountNotSufficientException;
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

    // transactional - must be encompassed in a single unit of work
    @Transactional
    public Boolean transferMoney(Integer withdrawalAccountID, Integer depositAccountID, Float transferAmount) {
        // method logic
        // ----------------------
        // 1. check that transferer exists
        Boolean transfererExists = false;
        BankAccount transferer = repository.getAccountByID(withdrawalAccountID);

        if (transferer != null) {
            transfererExists = true;
        }

        // 2. check that receiver exists
        Boolean receiverExists = false;
        BankAccount receiver = repository.getAccountByID(withdrawalAccountID);

        if (receiver != null) {
            receiverExists = true;
        }
        // 3. check that transferer is active/not blocked
        Boolean transfererAllowed = false;

        if (transferer.getIsActive() && !transferer.getIsBlocked()) {
            transfererAllowed = true;
        }

        // 4. check that receiver is active/not blocked
        Boolean receiverAllowed = false;

        if (receiver.getIsActive() && !receiver.getIsBlocked()) {
            receiverAllowed = true;
        }

        // 7. check that transferer has enough money to transfer to receiver

        Boolean enoughMoney = false;

        if (transferer.getBalance() > transferAmount) {
            enoughMoney = true;
        }

        if (transfererExists && receiverExists && transfererAllowed && receiverAllowed && enoughMoney) {
            // carry out the transfer operations
            // both of these must be successful in one unit of work
            // anywhere in this function (which is transactional) fails = rollback

            // 1. withdraw the amount from the transferer
            repository.withdraw(withdrawalAccountID, transferAmount);

            // 2. deposit the amount into the receiver
            repository.deposit(depositAccountID, transferAmount);
        } else {
            if (!transfererAllowed) {
                throw new AccountBlockedAndDisabledException("Transferer is either blocked or inactive.");
            }

            if (!receiverAllowed) {
                throw new AccountBlockedAndDisabledException("Receiver is either blocked or inactive.");
            }

            if (!enoughMoney) {
                throw new AmountNotSufficientException("Transferer does not have enough balance to transfer to receiver.");
            }
        }


        return true;
    }
    
}
