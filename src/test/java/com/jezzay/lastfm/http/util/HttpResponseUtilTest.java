package com.jezzay.lastfm.http.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;

public class HttpResponseUtilTest {

    @Test
    public void createSuccessHttpResponse_should_create_http_200_response_text() {
        String successHttpResponse = HttpResponseUtil.createSuccessHttpResponse("{\"data\":\"result\"}");
        assertThat(successHttpResponse,containsString("HTTP/1.1 200 OK"));
    }

    @Test
    public void createNotFoundHttpResponse_should_include_response_data() {
        String successHttpResponse = HttpResponseUtil.createNotFoundHttpResponse("{\"error\":\"Not Found\"}");
        assertThat(successHttpResponse,containsString("HTTP/1.1 404 Not Found"));
        assertThat(successHttpResponse,containsString("{\"error\":\"Not Found\"}"));
    }

    @Test
    public void createNotFoundHttpResponse_should_create_http_404_response_text() {
        String successHttpResponse = HttpResponseUtil.createNotFoundHttpResponse();
        assertThat(successHttpResponse,containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void createSuccessHttpResponse_should_include_json_content_type() {
        String successHttpResponse = HttpResponseUtil.createSuccessHttpResponse("{\"data\":\"result\"}");
        assertThat(successHttpResponse,containsString("Content-Type: application/json"));
    }

    @Test
    public void createInternalServerErrorHttpResponse_should_create_http_200_response_text() {
        String successHttpResponse = HttpResponseUtil.createInternalServerErrorHttpResponse("{\"error\":\"It Broke :(\"}");
        assertThat(successHttpResponse,containsString("HTTP/1.1 500 Internal Server Error"));
    }

    @Test
    public void createSuccessHttpResponse_should_include_content_length_of_response_data() {
        String responseData = "{\"data\":\"result\"}";
        int responseDataLength = responseData.length();
        String successHttpResponse = HttpResponseUtil.createSuccessHttpResponse(responseData);
        assertThat(successHttpResponse,containsString("Content-Length: " + responseDataLength));
    }

}