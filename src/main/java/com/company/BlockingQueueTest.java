package com.company;


import com.company.blocking_queue.Consumer;
import com.company.blocking_queue.Message;
import com.company.blocking_queue.Producer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {

    public static void main(String args[]) {
        BlockingQueue<Message> q = new ArrayBlockingQueue<Message>(10);
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
        System.out.println("Producer and Consumer has been started");
    }

}

