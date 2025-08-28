package org.example.comsumerProceducer;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MessageRepository {
    private String message;
    private boolean hasMessage = false;
    private final Lock lock = new ReentrantLock();

    public String read() {
        if (lock.tryLock()) {
            try {
                while (!hasMessage) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                hasMessage = false;
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("** Read blocked");
            hasMessage = false;
        }

        return message;
    }

    public void write(String message) {
        if (lock.tryLock()) { //tryLock dùng để kiểm tra xem luồng này có đang chiếm được 1 cái lock nào của object không? Đồng thời nó sẽ lập tức chiếm lấy lock ngay khi có cơ hội
            try {
                while (hasMessage) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                hasMessage = true;
            } finally {
                lock.unlock(); // Giải phóng object khỏi lock
            }
        } else {
            System.out.println("** Write blocked");
            hasMessage = true;
        }

        this.message = message;
    }
}

class MessageWriter implements Runnable {

    private MessageRepository messageRepository;

    private final String text = """
            Humpty Dumpty sat on a wall,
            Humpty Dumpty had a great fall,
            All the king's horses and all the king's men,
            Couldn't put Humpty together again.
            """;

    public MessageWriter(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void run() {
        Random random = new Random();
        var lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            messageRepository.write(lines[i]);
            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        messageRepository.write("Finished");
    }
}

class MessageReader implements Runnable {

    private MessageRepository messageRepository;

    public MessageReader(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void run() {
        Random random = new Random();
        String latestMessage = "";

        do {
            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            latestMessage = messageRepository.read();
            System.out.println(latestMessage);
        } while (!latestMessage.equals("Finished"));
    }
}

public class Main {

    public static void main(String[] args) {
        MessageRepository messageRepository = new MessageRepository();
        var messageWriter = new MessageWriter(messageRepository);
        var messageReader = new MessageReader(messageRepository);

        var threadWriter = new Thread(messageWriter);
        var threadReader = new Thread(messageReader);

        threadWriter.setUncaughtExceptionHandler((thread, exc) -> {
            System.out.println("Writer had exception: " + exc);
            if (threadReader.isAlive()) {
                System.out.println("Going to interrupt the reader");
                threadReader.interrupt();
            }
        });

        threadReader.setUncaughtExceptionHandler((thread, exc) -> {
            System.out.println("Thread had exception: " + exc);
            if (threadWriter.isAlive()) {
                System.out.println("Going to interrupt the writer");
                threadWriter.interrupt();
            }
        });

        threadWriter.start();
        threadReader.start();
    }

}
