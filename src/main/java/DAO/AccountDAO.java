package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

public class AccountDAO {
    
    public Account createAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_account_id = (int) pkeyResultSet.getLong(1);
                    return new Account(generated_account_id, account.getUsername(), account.getPassword());
                }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account login(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT username, account_id FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                return account;
            }

            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
        
    }
}
