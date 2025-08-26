package org.example.comsumerProceducer;

import java.util.Random;

class MessageRepository {
    private String message;
    private boolean hasMessage = false;

    public synchronized String read() {
        while (!hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Reading message");
        hasMessage = false;
        notifyAll();
        return message;
    }

    public synchronized void write(String message) {
        while (hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        hasMessage = true;
        notifyAll();
        this.message = message;
        System.out.println("Message written: " + message);
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

        threadWriter.start();
        threadReader.start();
    }

}
