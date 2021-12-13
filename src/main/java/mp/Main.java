package mp;

import mp.atm.Account;
import mp.atm.DatabaseConnection;
import mp.atm.util.Result;
import mp.gui.AccountManager;
import mp.gui.LoginRegister;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection database = new DatabaseConnection("", "", "");

        LoginRegister loginRegisterPage = new LoginRegister();

        loginRegisterPage.setLoginCallback(loginAl -> {
            Result<Account> loginResult = database.Login(loginRegisterPage.getUsernameField().getText(), String.valueOf(loginRegisterPage.getPasswordField().getPassword()));

            if (loginResult.getValue() == null) {
                JOptionPane.showMessageDialog(loginRegisterPage.getFrame(), loginResult.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Point accountManagerPageLocation = loginRegisterPage.getFrame().getLocation();
            loginRegisterPage.getFrame().dispose();

            Account account = loginResult.getValue();
            AccountManager accountManagerPage = new AccountManager(account::getBalance, accountManagerPageLocation);

            accountManagerPage.setDepositCallback(depositAl -> {
                Result<Boolean> depositResult = account.deposit(accountManagerPage.getInputAmount());

                if (!depositResult.getValue()) {
                    JOptionPane.showMessageDialog(null, depositResult.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null, depositResult.getMessage());
            });

            accountManagerPage.setWithdrawCallback(withdrawAl -> {
                Result<Boolean> withdrawResult = account.withdraw(accountManagerPage.getInputAmount());

                if (!withdrawResult.getValue()) {
                    JOptionPane.showMessageDialog(null, withdrawResult.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null, withdrawResult.getMessage());
            });
        });


        loginRegisterPage.setRegisterCallback(registerAl -> {
            Result<Boolean> registerResult = database.Register(loginRegisterPage.getUsernameField().getText(), String.valueOf(loginRegisterPage.getPasswordField().getPassword()));

            if (!registerResult.getValue()) {
                JOptionPane.showMessageDialog(null, registerResult.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, registerResult.getMessage());
        });
    }
}
