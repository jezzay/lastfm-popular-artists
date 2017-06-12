package com.jezzay.lastfm.controller.impl;

import static org.junit.Assert.assertEquals;

import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

public class TopArtistByGeoControllerTest {

    private TopArtistByGeoController controller;

    @Before
    public void setUp() throws Exception {
        this.controller = new TopArtistByGeoController();
    }

    @Test
    public void processRequest_should_extract_country_from_request_path() {
        IncomingApiHttpRequest request = new IncomingApiHttpRequest();
        request.processPath("GET /api/geo/top-artist/Australia/ HTTP/1.1\n");
        ApiResponse response
                = controller.processRequest(Pattern.compile("^/api/geo/top-artist/([a-zA-Z]*)/$"), request);
        assertEquals("Australia", response.getResponseData());

    }
}