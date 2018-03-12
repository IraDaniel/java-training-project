package com.company.thread_pool;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool implements Executor {


    private final Queue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private List<Thread> threads = new ArrayList<>();
    private volatile boolean isRunning = true;

    public ThreadPool(int nThreads) {
        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread(new TaskWorker());
            thread.start();
            threads.add(thread);
        }
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            if(workQueue.offer(command)){
                command.run();
            };

        }
    }

    public void shutdown() {
        isRunning = false;
    }

    private class TaskWorker implements Runnable {
        @Override
        public void run() {
            if (isRunning) {
                Runnable nextTask = workQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}


