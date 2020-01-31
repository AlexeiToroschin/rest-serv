package com.organization.test;

import org.apache.camel.component.http4.HttpClientConfigurer;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;

public class TestHttpClientConfigurer implements HttpClientConfigurer {

    private int maxAttempConnect;

    public TestHttpClientConfigurer(int maxAttempConnect){
        this.maxAttempConnect = maxAttempConnect;
    }

    @Override
    public void configureHttpClient(HttpClientBuilder httpClientBuilder) {
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(maxAttempConnect,false));
    }
}
