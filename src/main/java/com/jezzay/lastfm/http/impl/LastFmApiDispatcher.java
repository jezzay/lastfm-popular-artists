package com.jezzay.lastfm.http.impl;


import com.jezzay.lastfm.controller.ApiController;
import com.jezzay.lastfm.controller.impl.TopArtistByGeoController;
import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.http.ApiRequestDispatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class LastFmApiDispatcher implements ApiRequestDispatcher {
    private Map<Pattern, ApiController> apiMapping = new HashMap<>();

    public LastFmApiDispatcher() {
        this.apiMapping.put(Pattern.compile("^/api/geo/top-artist/([a-zA-Z]*)/([0-9])/$"), new TopArtistByGeoController());
    }

    @Override
    public ApiResponse dispatchRequest(IncomingApiHttpRequest request) {
        Optional<Pattern> mapping = this.apiMapping
                .keySet()
                .stream()
                .filter(pattern -> pattern.matcher(request.getPath()).find())
                .findFirst();

        if (mapping.isPresent()) {
            Pattern pattern = mapping.get();
            return this.apiMapping.get(pattern).processRequest(pattern, request);
        } else {
            return ApiResponse.createFailure(String.format("No API mapping found for %s", request.getPath()));
        }
    }

    void setApiMapping(Map<Pattern, ApiController> apiMapping) {
        this.apiMapping = apiMapping;
    }
}
