package com.jezzay.lastfm.http;

import static java.lang.String.format;

import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.http.impl.LastFmApiDispatcher;
import com.jezzay.lastfm.http.util.HttpResponseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class RequestHandler {
    private final LastFmApiDispatcher apiDispatcher;

    public RequestHandler() {
        this.apiDispatcher = new LastFmApiDispatcher();
    }

    RequestHandler(LastFmApiDispatcher dispatcher) {
        this.apiDispatcher = dispatcher;
    }

    void handleRequest(BufferedReader reader, OutputStreamWriter writer) throws IOException {
        IncomingApiHttpRequest request = processIncomingRequest(reader);
        if (request.isStatusCheck()) {
            writer.write(HttpResponseUtil.createSuccessHttpResponse("ok"));
            return;
        }
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
            e.printStackTrace();
            System.err.println(e.getMessage());
            return HttpResponseUtil.createInternalServerErrorHttpResponse(
                    format("{\"error\":\"%s\"}", e.getClass().getName()));
        }
    }
}
