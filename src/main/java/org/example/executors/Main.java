package org.example.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ColorThreadFactory implements ThreadFactory {

    private String threadName;

    public ColorThreadFactory(ThreadColor threadName) {
        this.threadName = threadName.name();
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, threadName);
    }
}

public class Main {

    public static void main(String[] args) {
        var blueExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_BLUE));
        blueExecutor.execute(Main::countDown);
//        blueExecutor.shutdown();

        var yellowExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_YELLOW));
        yellowExecutor.execute(Main::countDown);
        yellowExecutor.shutdown();

        var redExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_RED));
        redExecutor.execute(Main::countDown);
        redExecutor.shutdown();
    }

    public static void notmain(String[] args) {
        var blue = new Thread(Main::countDown, ThreadColor.ANSI_BLUE.name());
        var red = new Thread(Main::countDown, ThreadColor.ANSI_RED.name());
        var yellow = new Thread(Main::countDown, ThreadColor.ANSI_YELLOW.name());

        blue.start();
        red.start();
        yellow.start();

        try {
            blue.join();
            red.join();
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void countDown() {
        var threadName = Thread.currentThread().getName();
        var threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName);

        } catch (IllegalArgumentException ignore) {
            // User may pass a bad color name, will just ignore that error
        }

        var color = threadColor.getColor();
        for (int i = 20; i >= 0; i--) {
            System.out.println(color + " " + threadName.replace("ANSI_", "") + " " + i);
        }
    }
}
