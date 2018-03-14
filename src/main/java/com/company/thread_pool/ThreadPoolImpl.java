package com.company.thread_pool;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolImpl implements ThreadPool {

    private volatile boolean isRunning = true;
    private final Queue<Runnable> workQueue;
    private List<Worker> workers;

    public ThreadPoolImpl(int poolSize) {
        workQueue = new LinkedBlockingQueue<>();
        workers = new ArrayList<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker("" + i);
            workers.add(worker);
            workers.get(i).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (!workQueue.offer(command)) {
            System.out.println("Queue is full");
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    public boolean isTerminated() {
        return workQueue.isEmpty();
    }

    private class Worker extends Thread {

        Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (isRunning) {
                Runnable nextTask = workQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}


