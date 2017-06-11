package com.jezzay.lastfm.http;

import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.http.impl.LastFmApiDispatcher;
import com.jezzay.lastfm.http.util.HttpResponseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class LastFmHttpConnectionHandler implements Runnable {
    private final Socket socket;
    private final LastFmApiDispatcher apiDispatcher;

    LastFmHttpConnectionHandler(Socket socket) {
        this.socket = socket;
        this.apiDispatcher = new LastFmApiDispatcher();
    }

    LastFmHttpConnectionHandler(Socket socket, LastFmApiDispatcher apiDispatcher) {
        this.socket = socket;
        this.apiDispatcher = apiDispatcher;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

                OutputStreamWriter writer
                        = new OutputStreamWriter(this.socket.getOutputStream())
        ) {
            handleRequest(reader, writer);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    void handleRequest(BufferedReader reader, OutputStreamWriter writer) throws IOException {
        IncomingApiHttpRequest request = processIncomingRequest(reader);
        if (!request.isApiRequest()) {
            writer.write(HttpResponseUtil.createNotFoundHttpResponse());
        } else {
            String response = processApiRequest(request);
            writer.write(response);
        }
    }

    private IncomingApiHttpRequest processIncomingRequest(BufferedReader reader)
            throws IOException {
        IncomingApiHttpRequest incomingApiHttpRequest = new IncomingApiHttpRequest();
        String httpRequestLine = reader.readLine();
        incomingApiHttpRequest.processPath(httpRequestLine);
        httpRequestLine = reader.readLine();
        while (httpRequestLine != null) {
            if (httpRequestLine.equals("")) {
                break;
            }
//            System.out.println(httpRequestLine);
            incomingApiHttpRequest.processHeader(httpRequestLine);
            httpRequestLine = reader.readLine();
        }
        return incomingApiHttpRequest;
    }

    private String processApiRequest(IncomingApiHttpRequest incomingApiHttpRequest) {
        try {
            ApiResponse response = apiDispatcher.dispatchRequest(incomingApiHttpRequest);
            if (!response.isSuccessful()) {
                return HttpResponseUtil.createNotFoundHttpResponse(response.error());
            }
            return HttpResponseUtil.createSuccessHttpResponse(response.resultAsJson());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return HttpResponseUtil.createInternalServerErrorHttpResponse(String.format("{\"error\":\"%s\"}", e.getClass().getName()));
        }
    }

}
