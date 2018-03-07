package com.company;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletLoadTest {

    private static final String URL = "http://localhost:8080/java-training-project/hello";

    @Test
    public void test() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL);

        HttpResponse response = client.execute(post);

//        for (int i = 0; i < 10; i++) {
//              executor.execute()
//        }
//        executor.shutdown();
//        while (!executor.isTerminated()) {
//        }

        System.out.println("Status:" + response.getStatusLine().getStatusCode());
    }
}
