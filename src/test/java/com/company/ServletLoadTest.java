package com.company;


import com.company.servlet.ServletCaller;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletLoadTest {

    private static final String URL = "http://localhost:8080/java-training-project/hello";

    @Test
    public void test() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 200; i++) {
            ServletCaller caller = new ServletCaller(URL, i);
            executor.execute(caller);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

    }
}
