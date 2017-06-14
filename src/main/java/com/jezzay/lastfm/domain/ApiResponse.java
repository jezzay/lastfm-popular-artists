package com.jezzay.lastfm.domain;

import java.util.List;

public class ApiResponse {
    private Object responseData;
    private String error;

    public static ApiResponse createFailure(String reason) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(String.format("{\"error\":\"%s\"}", reason));
        return apiResponse;
    }

    public static ApiResponse createSuccess(Object responseData) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponseData(responseData);
        return apiResponse;
    }

    public static ApiResponse createSuccess(List responseData) {
        StringBuilder resultJson = new StringBuilder();
        for (Object data : responseData) {
            resultJson.append(data.toString()).append(",");
        }
        // remove trailing ,
        resultJson.deleteCharAt(resultJson.length() - 1);
        return ApiResponse.createSuccess("{\"data\":[ " + resultJson.toString() + "]}");
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
