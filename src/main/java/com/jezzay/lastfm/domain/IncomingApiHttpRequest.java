package com.jezzay.lastfm.domain;

import java.util.HashMap;
import java.util.Map;

public class IncomingApiHttpRequest {
    private Map<String, String> requestHeaders = new HashMap<>();
    private String path = "";

    public void processPath(String httpRequestLine) {
        // GET <PATH> HTTP/1.1 -> <PATH>
        if (httpRequestLine != null) {
            String[] pathAndHttpVersion = httpRequestLine.split("HTTP");
            String methodAndPath = pathAndHttpVersion[0];
            this.path = methodAndPath.substring(
                    methodAndPath.indexOf("/"),
                    methodAndPath.length()).trim();
        }

    }

    public void processHeader(String httpRequestHeader) {
        String[] header = httpRequestHeader.split(":");
        requestHeaders.put(header[0], header[1].trim());
    }

    public boolean isApiRequest() {
        return this.path.startsWith("/api/");
    }

    public boolean isStatusCheck() {
        return this.path.startsWith("/api/status");
    }

    public String getPath() {
        return this.path;
    }

    public String getHeader(String header) {
        return this.requestHeaders.get(header);
    }
}
