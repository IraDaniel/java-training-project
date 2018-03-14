package com.company.thread_pool;


import java.util.concurrent.Executor;

public interface ThreadPool extends Executor{

    void execute(Runnable command);
    void shutdown();
    boolean isTerminated();
}
