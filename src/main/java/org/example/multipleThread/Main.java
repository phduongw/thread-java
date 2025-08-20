package org.example.multipleThread;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        var stopWatch = new StopWatch(TimeUnit.SECONDS);
        var green = new Thread(stopWatch::countDown, ThreadColor.ANSI_GREEN.name());
        var red = new Thread(stopWatch::countDown, ThreadColor.ANSI_RED.name());
        var purple = new Thread(() -> stopWatch.countDown(7), ThreadColor.ANSI_PURPLE.name());
        green.start();
        purple.start();
        red.start();
    }

}

class StopWatch {
    private final TimeUnit timeUnit;
    private int i;

    public StopWatch(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void countDown() {
        countDown(5);
    }

    public void countDown(int unitCount) {
        String threadName = Thread.currentThread().getName();
        var threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName);

        } catch (IllegalArgumentException ignore) {

        }

        var color = threadColor.getColor();
        for (i = unitCount; i > 0; i--) {
            try {
                timeUnit.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("%s%s Thread : i = %d%n", color, threadName, i);
        }
    }
}
