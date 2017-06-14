package com.jezzay.lastfm.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.http.impl.LastFmApiDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

@RunWith(MockitoJUnitRunner.class)
public class RequestHandlerTest {

    private RequestHandler requestHandler;
    @Mock
    private LastFmApiDispatcher dispatcher;

    @Mock
    private BufferedReader reader;
    @Mock
    private OutputStreamWriter writer;

    @Before
    public void setUp() throws Exception {
        this.requestHandler = new RequestHandler(dispatcher);
    }

    @Test
    public void handleRequest_writes_error_response_when_not_an_api_request() throws IOException {
        given(reader.readLine()).willReturn("GET /index HTTP/1.1\n")
                .willReturn("Connection: keep-alive")
                .willReturn("");
        requestHandler.handleRequest(reader, writer);
        verify(writer).write("HTTP/1.1 404 Not Found \n Content-Type: application/json \n Content-Length: 0 \n\n ");
    }

    @Test
    public void handleRequest_dispatches_request_response_when_request_is_an_api_request() throws IOException {
        given(reader.readLine()).willReturn("GET /api/geo/top-artist/Australia/ HTTP/1.1\n")
                .willReturn("Connection: keep-alive")
                .willReturn("");
        given(dispatcher.dispatchRequest(any())).willReturn(ApiResponse.createSuccess("It worked"));
        requestHandler.handleRequest(reader, writer);
        verify(dispatcher).dispatchRequest(any(IncomingApiHttpRequest.class));
    }


}