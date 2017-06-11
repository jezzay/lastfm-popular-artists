package com.jezzay.lastfm.http.util;

public final class HttpResponseUtil {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String HTTP_OK = "200 OK";
    private static final String HTTP_NOT_FOUND = "404 Not Found";
    private static final String HTTP_INTERNAL_SERVER_FAILURE = "500 Internal Server Error";
    private static final String HTTP_JSON_CONTENT_TYPE = "Content-Type: application/json";
    private static final String HTTP_CONTENT_LENGTH_TEMPLATE = "Content-Length: %s";

    private HttpResponseUtil(){}

    public static String createSuccessHttpResponse(String jsonData) {
        return createHttpResponse(HTTP_OK, jsonData);
    }

    public static String createNotFoundHttpResponse(String error) {
        return createHttpResponse(HTTP_NOT_FOUND, error);
    }

    public static String createInternalServerErrorHttpResponse(String error) {
        return createHttpResponse(HTTP_INTERNAL_SERVER_FAILURE, error);
    }

    public static String createNotFoundHttpResponse() {
        return createHttpResponse(HTTP_NOT_FOUND, "");
    }

    private static String createHttpStatus(String status) {
        return String.format("%s %s", HTTP_VERSION, status);
    }

    private static String createHttpResponse(String status, String jsonData) {
        String contentLength = String.format(HTTP_CONTENT_LENGTH_TEMPLATE, jsonData.length());
        return String.format("%s \n %s \n %s \n\n %s",
                createHttpStatus(status),
                HTTP_JSON_CONTENT_TYPE,
                contentLength,
                jsonData);
    }
}
