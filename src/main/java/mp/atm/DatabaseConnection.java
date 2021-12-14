package mp.atm;

import mp.atm.util.Result;

import java.sql.*;

public class DatabaseConnection {
    private Connection conn = null;

    public DatabaseConnection(String dbURL, String username, String password) {
        try {
            this.conn = DriverManager.getConnection(dbURL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("SqlResolve")
    public Result<Account> Login(String username, String password) {

        if (username.isEmpty() || password.isEmpty() || username.length() >= 50 || password.length() >= 50)
            return new Result<>("username or password can not be empty", null);

        try {
            PreparedStatement query;
            
            query = this.conn.prepareStatement("SELECT * FROM `atm` WHERE username=?");
            query.setString(1, username);

            ResultSet queryResult = query.executeQuery();

            if (!queryResult.next())
                return new Result<>("account not found", null);

            if (!queryResult.getString("userpass").equals(password))
                return new Result<>("incorrect password", null);

            return new Result<>(null, new Account(queryResult.getFloat("balance"), username, this.conn));
        } catch (Exception e) {
            return new Result<>(e.getMessage(), null);
        }
    }

    @SuppressWarnings("SqlResolve")
    public Result<Boolean> Register(String username, String password) {
        
        if (username.isEmpty() || password.isEmpty() || username.length() >= 50 || password.length() >= 50)
            return new Result<>("username or password can not be empty", false);

        if (!Login(username, password).getMessage().equals("account not found"))
            return new Result<>("account already registered or username is taken", false);

        try {
            PreparedStatement query;

            //username,password,balance
            query = conn.prepareStatement("INSERT INTO `atm` VALUES(?, ?, '0')");
            query.setString(1, username);
            query.setString(2, password);

            int rowsAffected = query.executeUpdate();
            if (rowsAffected != 1)
                return new Result<>("failed to register", false);

            return new Result<>("account registered successfully", true);
        } catch (Exception e) {
            return new Result<>(e.getMessage(), false);
        }
    }

}
