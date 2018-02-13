package com.company;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {

    public static void main(String args[]) {
        BlockingQueue<Message> q = new ArrayBlockingQueue<>(10);
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
        System.out.println("Producer and Consumer has been started");
    }

}

class Producer implements Runnable {
    private final BlockingQueue<Message> queue;

    Producer(BlockingQueue<Message> q) {
        queue = q;
    }

    @Override
    public void run() {
        //produce messages
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("" + i);
            try {
                Thread.sleep(i);
                queue.put(msg);
                System.out.println("Produced " + msg.getMsg());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //adding exit message
        Message msg = new Message("exit");
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Object produce() {
        return "test";
    }
}

class Consumer implements Runnable {

    private BlockingQueue<Message> queue;

    public Consumer(BlockingQueue<Message> q) {
        this.queue = q;
    }

    @Override
    public void run() {
        try {
            Message msg;
            //consuming messages until exit message is received
            while ((msg = queue.take()).getMsg() != "exit") {
                Thread.sleep(10);
                System.out.println("Consumed " + msg.getMsg());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void consume(Object x) {

    }
}

class Message {
    private String msg;

    public Message(String str) {
        this.msg = str;
    }

    public String getMsg() {
        return msg;
    }
}
