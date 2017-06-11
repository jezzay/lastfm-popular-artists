package com.jezzay.lastfm.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class IncomingApiHttpRequestTest {

    @Test
    public void processPath_should_process_http_request_into_request_path() {
        IncomingApiHttpRequest incomingApiHttpRequest = new IncomingApiHttpRequest();
        String httpRequestLine = "GET /api/geo/top-artist/Australia/ HTTP/1.1\n";
        incomingApiHttpRequest.processPath(httpRequestLine);
        assertEquals("/api/geo/top-artist/Australia/", incomingApiHttpRequest.getPath());
    }

    @Test
    public void processHeader_should_process_header_line() {
        IncomingApiHttpRequest incomingApiHttpRequest = new IncomingApiHttpRequest();
        incomingApiHttpRequest.processHeader("Connection: keep-alive");
        assertEquals(incomingApiHttpRequest.getHeader("Connection"), "keep-alive");
    }

    @Test
    public void isApiRequest_should_return_true_when_request_is_on_an_api_path() {
        IncomingApiHttpRequest incomingApiHttpRequest = new IncomingApiHttpRequest();
        String httpRequestLine = "GET /api/geo/top-artist/Australia/ HTTP/1.1\n";
        incomingApiHttpRequest.processPath(httpRequestLine);
        assertTrue(incomingApiHttpRequest.isApiRequest());
    }

    @Test
    public void isApiRequest_should_return_false_when_request_is_not_an_api_path() {
        IncomingApiHttpRequest incomingApiHttpRequest = new IncomingApiHttpRequest();
        String httpRequestLine = "GET /index HTTP/1.1\n";
        incomingApiHttpRequest.processPath(httpRequestLine);
        assertFalse(incomingApiHttpRequest.isApiRequest());
    }
}