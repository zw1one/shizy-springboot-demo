package com.shizy.utils.http;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * 由于httpclient不支持get+body的传输方式，因此需要自行实现
 */
public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {

    private final static String METHOD_NAME = "GET" ;

    public HttpGetWithEntity() {
        super();
    }

    public HttpGetWithEntity(final URI uri) {
        super();
        setURI(uri);
    }

    HttpGetWithEntity(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

}
