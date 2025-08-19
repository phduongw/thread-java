package org.example;

public class CustomThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.print(" 1 ");
            try {
                Thread.sleep(500); //Delay between each count
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
