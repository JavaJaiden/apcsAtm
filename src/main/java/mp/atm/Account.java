package mp.atm;

import mp.atm.util.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Account {
    private float balance;
    private final String username;
    private final Connection conn;

    public Account(float balance, String username, Connection conn) {
        this.balance = balance;
        this.conn = conn;
        this.username = username;
    }

    public float getBalance() {
        return balance;
    }

    private void setBalance(float balance) {
        this.balance = balance;
    }

    @SuppressWarnings("SqlResolve")
    public Result<Boolean> deposit(float amount) {
        try {
            PreparedStatement query;
            query = this.conn.prepareStatement("UPDATE `atm` SET balance=? WHERE username=?");
            query.setString(1, String.valueOf(this.getBalance() + amount));
            query.setString(2, this.username);

            int rowsAffected = query.executeUpdate();
            if (rowsAffected != 1)
                return new Result<>("failed to deposit money, internal error", false);

            this.setBalance(this.getBalance() + amount);
            return new Result<>(String.format("deposit successful, new balance %.2f", this.getBalance()), true);

        } catch (Exception e) {
            return new Result<>(e.getMessage(), false);
        }
    }

    @SuppressWarnings("SqlResolve")
    public Result<Boolean> withdraw(float amount) {
        try {
            if (this.getBalance() - amount < 0)
                return new Result<>(String.format("not enough in account to withdraw %.2f", amount), false);

            PreparedStatement query;
            query = this.conn.prepareStatement("UPDATE `atm` SET balance=? WHERE username=?");
            query.setString(1, String.valueOf(this.getBalance() - amount));
            query.setString(2, username);

            int rowsAffected = query.executeUpdate();

            if (rowsAffected != 1)
                return new Result<>("failed to withdraw money, internal error", false);

            this.setBalance(this.getBalance() - amount);
            return new Result<>(String.format("withdraw successful, new balance %.2f", this.getBalance()), true);

        } catch (Exception e) {
            return new Result<>(e.getMessage(), false);
        }
    }
}

