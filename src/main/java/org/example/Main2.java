package org.example;

public class Main2 {
    public static void main(String[] args) {
        System.out.println("Main Thread is running...");
        try {
            System.out.println("Main Thread paused for 1 second");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        var thread = new Thread(() -> {
            var threadName = Thread.currentThread().getName();
            System.out.println(threadName + " should take 10 dots to run");
            for (int i = 0; i < 10; i++) {
                System.out.print(" . ");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("\nWhoops!!! " + threadName + " was interrupted!");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("\n" + threadName + " has finished running");
        });

        var installedThread = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(250);
                    System.out.println("Installation Step " + (i + 1) + " is done!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "InstallThread");

        System.out.println(thread.getName() + " is starting...");

        //Khi mà main thread bị delay thì thread đang chạy cùng lúc vẫn sẽ chạy
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        thread.interrupt();

        var threadMonitor = new Thread(() -> {
            var now = System.currentTimeMillis();
            while (thread.isAlive()) {
                try {
                    thread.sleep(1000);
                    if (System.currentTimeMillis() - now > 2000) {
                        thread.interrupt();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        threadMonitor.start();
        System.out.println("Main Thread would continue here");

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (thread.isInterrupted()) {
            installedThread.start();
        } else {
            System.out.println("Previous thread was interrupted," + installedThread.getName() + " can't run");
        }
    }
}
