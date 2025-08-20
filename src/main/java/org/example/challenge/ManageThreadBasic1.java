package org.example.challenge;

public class ManageThreadBasic1 {

    public static void main(String[] args) {
        var thread1 = new CustomThread();
        thread1.start();

        var thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.print(" 2 ");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread1.interrupt();
    }

}
