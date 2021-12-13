package mp.gui;

import java.util.concurrent.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class AccountManager {
    private JButton depositButton = null;
    private JButton withdrawButton = null;
    private JTextField moneyAmountField = null;
    private JFrame frame = null;
    private JLabel moneyAmountLabel = null;
    private Callable<Float> getBalance = null;

    public AccountManager(Callable<Float> getBalanceCallback, Point frameLocation) {
        try {
            JLabel panel = new JLabel();
            this.getBalance = getBalanceCallback;

            this.moneyAmountLabel = new JLabel(String.format("$%.2f", getBalance.call()), SwingConstants.CENTER);
            this.moneyAmountLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
            this.moneyAmountLabel.setBounds(105, 8, 175, 35);

            this.moneyAmountField = new JTextField();
            this.moneyAmountField.setBounds(100, 40, 193, 28);

            this.depositButton = new JButton("Deposit");
            this.depositButton.setBounds(95, 110, 100, 25);
            this.depositButton.setForeground(Color.WHITE);
            this.depositButton.setBackground(Color.BLACK);


            this.withdrawButton = new JButton("Withdraw");
            this.withdrawButton.setBounds(200, 110, 100, 25);
            this.withdrawButton.setForeground(Color.WHITE);
            this.withdrawButton.setBackground(Color.BLACK);

            panel.setLayout(null);
            panel.add(moneyAmountLabel);
            panel.add(this.moneyAmountField);
            panel.add(this.depositButton);
            panel.add(this.withdrawButton);


            frame = new JFrame();
            frame.setTitle("Account Management");
            frame.setLocation(frameLocation);
            frame.add(panel);
            frame.setSize(new Dimension(400, 200));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDepositCallback(ActionListener actionListener) {
            this.depositButton.addActionListener(event -> {
                try {
                    actionListener.actionPerformed(event);
                    moneyAmountLabel.setText(String.format("$%.2f", getBalance.call()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    public void setWithdrawCallback(ActionListener actionListener) {
        this.withdrawButton.addActionListener(event -> {
            try {
                actionListener.actionPerformed(event);
                moneyAmountLabel.setText(String.format("$%.2f", getBalance.call()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public float getInputAmount() {
        try {
            return Float.parseFloat(this.moneyAmountField.getText());
        } catch (Exception e) {
            return 0.f;
        }
    }

    public JFrame getFrame() {
        return this.frame;
    }

}
