package com.tedu.web;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebPool { // 线程池
    ExecutorService executor;

    public WebPool(int nThreads) {
        executor = Executors.newFixedThreadPool(nThreads);
    }

    public void addTask(Runnable task) {
        executor.execute(task);
    }

    public void shutdown() {
        executor.shutdown();
    }


}
