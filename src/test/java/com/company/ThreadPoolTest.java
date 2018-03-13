package com.company;


import com.company.thread_pool.ThreadPool;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {

    @Test
    public void testSystemThreadPool(){
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread("" + i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }


    @Test
    public void testThreadPool(){
        ThreadPool executor = new ThreadPool(5);

        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread("" + i);
            executor.execute(worker);
        }
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){

        }
        executor.shutdown();
        System.out.println("Finished all threads");
    }
}
