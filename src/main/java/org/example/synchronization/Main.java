package org.example.synchronization;

public class Main {

    public static void main(String[] args) {
        var companyAccount = new BankAccount(10000);
        var thread1 = new Thread(() -> companyAccount.withdraw(2500));

        var thread2 = new Thread(() -> companyAccount.deposit(5000));

        var thread3 = new Thread(() -> companyAccount.withdraw(2500));

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Final Balance: %.0f", companyAccount.getBalance());
    }

}
