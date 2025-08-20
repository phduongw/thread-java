package org.example.challenge;

public class CustomThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i <= 10; i += 2) {
            System.out.print("Odd Thread: " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Odd Thread was interrupted");
                break;
            }
        }
    }
}
