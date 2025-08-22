package org.example.synchronization;

public class BankAccount {

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        try {
            System.out.println("TELLING WITH THE PERSON......");
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Với cách đặt synchronized ở đây, nghĩa là block code nằm bên trong mới chỉ cần chờ đợi luồng khác thực thi xong object.
        synchronized (this) {
            double orgBalance = balance;
            balance += amount;
            System.out.printf("STARTING BALANCE: %.0f, DEPOSIT (%.0f)" + " NEW BALANCE: %.0f%n", orgBalance, amount, balance);
        }
    }

    public synchronized void withdraw(double amount) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double orgBalance = balance;
        if (amount <= balance) {
            balance -= amount;
            System.out.printf("STARTING BALANCE: %.0f, WITHDRAW (%.0f)" + " NEW BALANCE: %.0f%n", orgBalance, amount, balance);
            return;
        }

        System.out.printf("STARTING BALANCE: %.0f, WITHDRAW (%.0f)" + " INSUFFICIENT FUNDS", orgBalance, amount);

    }
}
