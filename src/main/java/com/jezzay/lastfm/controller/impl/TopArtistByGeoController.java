package com.jezzay.lastfm.controller.impl;

import com.jezzay.lastfm.controller.ApiController;
import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopArtistByGeoController implements ApiController {

    @Override
    public ApiResponse processRequest(Pattern pathPattern, IncomingApiHttpRequest httpRequest) {
        Matcher matcher = pathPattern.matcher(httpRequest.getPath());
        if (matcher.find()) {
            String country = matcher.group(1);
            // TODO: call service here
            return ApiResponse.createSuccess(country);
        }
        return ApiResponse.createFailure("Not able to extract country information from request");
    }
}
