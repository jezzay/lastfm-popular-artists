package com.jezzay.lastfm.domain;

public class ApiResponse {
    private Object responseData;
    private String error;

    public static ApiResponse createFailure(String reason) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(String.format("{\"error\":\"%s\"}", reason));
        return apiResponse;
    }

    public boolean isSuccessful() {
        return this.error == null;
    }

    public String error() {
        return this.error;
    }

    public String resultAsJson() {
        return isSuccessful() ? this.responseData.toString() : null;
    }

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
