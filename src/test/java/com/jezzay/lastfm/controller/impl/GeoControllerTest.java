package com.jezzay.lastfm.controller.impl;

import static org.mockito.Mockito.verify;

import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.service.GeoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.regex.Pattern;

@RunWith(MockitoJUnitRunner.class)
public class GeoControllerTest {

    private GeoController controller;
    @Mock
    private GeoService geoService;

    @Before
    public void setUp() throws Exception {
        this.controller = new GeoController(geoService);
    }

    @Test
    public void processRequest_should_extract_country_from_request_path() throws ParserConfigurationException,
            SAXException, IOException {
        IncomingApiHttpRequest request = new IncomingApiHttpRequest();
        request.processPath("GET /api/geo/top-artist/Australia/1/ HTTP/1.1\n");
        ApiResponse response
                = controller.processRequest(Pattern.compile("^/api/geo/top-artist/([a-zA-Z]*)/([0-9])/$"), request);
        verify(geoService).findTopArtistsFor("Australia", "1");
    }
}