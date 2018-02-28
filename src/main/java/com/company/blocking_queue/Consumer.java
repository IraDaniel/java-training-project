package com.company.blocking_queue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Irina_Daniel on 2/28/2018.
 */
public class Consumer implements Runnable {

    private BlockingQueue<Message> queue;

    public Consumer(BlockingQueue<Message> q) {
        this.queue = q;
    }

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
