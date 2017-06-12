package com.jezzay.lastfm.controller.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.service.TopArtistByGeoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.regex.Pattern;

@RunWith(MockitoJUnitRunner.class)
public class TopArtistByGeoControllerTest {

    private TopArtistByGeoController controller;
    @Mock
    private TopArtistByGeoService geoService;

    @Before
    public void setUp() throws Exception {
        this.controller = new TopArtistByGeoController(geoService);
    }

    @Test
    public void processRequest_should_extract_country_from_request_path() {
        IncomingApiHttpRequest request = new IncomingApiHttpRequest();
        request.processPath("GET /api/geo/top-artist/Australia/ HTTP/1.1\n");
        ApiResponse response
                = controller.processRequest(Pattern.compile("^/api/geo/top-artist/([a-zA-Z]*)/$"), request);
       verify(geoService).findTopArtistsFor("Australia");
    }
}