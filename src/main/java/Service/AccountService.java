package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public boolean register(Account account){

        if(account.username.length() > 0 && account.password.length() > 4){
            Account createdAccount = accountDAO.createAccount(account);
            if(createdAccount != null){
                return true;                    //Account was created
            }
        }
        return false;                           //Account was not created

    }

    public boolean login(Account account){

        Account loggedinAccount = accountDAO.login(account);

        if(loggedinAccount != null){
            return true;                        //Account was logged in
        }

        return false;                           //Account was not logged in
    }
    
}
