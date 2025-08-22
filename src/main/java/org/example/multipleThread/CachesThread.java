package org.example.multipleThread;

import java.util.concurrent.TimeUnit;

public class CachesThread {

    private volatile boolean flag = false;

    public void toggleFlag() {
        flag = !flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public static void main(String[] args) {
        var cachesThread = new CachesThread();
        var writerThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cachesThread.toggleFlag();
            System.out.println("A. Flag is set to " + cachesThread.isFlag());
        });



        var readerThread = new Thread(() -> {
            while (!cachesThread.isFlag()) {

            }

            System.out.println("B. Flag is " + cachesThread.isFlag());
        });

        writerThread.start();
        readerThread.start();
    }
}
