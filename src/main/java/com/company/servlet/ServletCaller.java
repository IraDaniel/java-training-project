package com.company.servlet;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class ServletCaller implements Runnable {

    private String url;
    private int commandNumber;

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " Start. Command = " + commandNumber);
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            HttpResponse response = client.execute(post);
            System.out.println(Thread.currentThread().getName() + " End. Command = " + commandNumber +
                    " with status:" + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServletCaller(String url, int commandNumber) {
        this.url = url;
        this.commandNumber = commandNumber;
    }
}
