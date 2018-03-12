package com.company;


import com.company.thread_pool.ThreadPool;
import org.junit.Test;

public class ThreadPoolTest {


    @Test
    public void testThreadPool(){
        ThreadPool executor = new ThreadPool(5);

        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread("" + i);
            executor.execute(worker);
        }
        executor.shutdown();
//        while (!executor.isTerminated()) {
//        }
        System.out.println("Finished all threads");
    }
}
