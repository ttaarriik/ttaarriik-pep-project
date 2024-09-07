package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account register(Account account){

        if(account.username.length() > 0 && account.password.length() > 4){
            Account createdAccount = accountDAO.createAccount(account);
            if(createdAccount != null){
                return createdAccount;                    //Account was created
            }
        }
        
        return new Account();
    }

    public Account login(Account account){
        Account account2 = accountDAO.login(account);

        return account2;
    }
    
}
