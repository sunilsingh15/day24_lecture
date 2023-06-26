package sg.edu.nus.iss.day24_lecture.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day24_lecture.exception.BankAccountNotFoundException;
import sg.edu.nus.iss.day24_lecture.model.BankAccount;

@Repository
public class BankAccountRepo {

    @Autowired
    JdbcTemplate template;

    private final String GET_ACCOUNT_SQL = "select * from bank_account where id = ?";
    private final String WITHDRAW_SQL = "update bank_account set balance = balance - ? where id = ?";
    private final String DEPOSIT_SQL = "update bank_account set balance = balance + ? where id = ?";
    private final String CREATE_ACCOUNT_SQL = "insert into bank_account (full_name, is_blocked, is_active, account_type, balance) values (?, ?, ?, ?, ?)";
    // private final String CREATE_ACCOUNT2_SQL = "insert into bank_account values
    // (?, ?, ?, ?, ?)";

    public BankAccount getAccountByID(int id) {
        List<BankAccount> bankAccounts = template.query(GET_ACCOUNT_SQL,
                BeanPropertyRowMapper.newInstance(BankAccount.class), id);

        if (bankAccounts.isEmpty()) {
            throw new BankAccountNotFoundException("Account does not exist.");
        }

        BankAccount bankAccount = bankAccounts.get(0);

        return bankAccount;
    }

    public Boolean withdraw(int accountID, Float withdrawAmount) {
        if ((template.update(WITHDRAW_SQL, withdrawAmount, accountID)) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean deposit(int accountID, Float depositAmount) {
        if ((template.update(DEPOSIT_SQL, depositAmount, accountID)) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean createAccount(BankAccount bankAccount) {
        if ((template.update(CREATE_ACCOUNT_SQL, bankAccount.getFullName(), bankAccount.getIsBlocked(),
                bankAccount.getIsActive(), bankAccount.getAccountType(), bankAccount.getBalance())) == 1) {
            return true;
        } else {
            return false;
        }
    }

}
