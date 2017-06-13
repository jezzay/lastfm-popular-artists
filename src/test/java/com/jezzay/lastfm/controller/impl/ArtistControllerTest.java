package com.jezzay.lastfm.controller.impl;

import static org.mockito.Mockito.verify;

import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.service.ArtistService;
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
public class ArtistControllerTest {

    private ArtistController controller;

    @Mock
    private ArtistService artistService;

    @Before
    public void setUp() throws Exception {
        this.controller = new ArtistController(artistService);
    }

    @Test
    public void processRequest_should_extract_artist_mbid_from_request_path()
            throws ParserConfigurationException, SAXException, IOException {
        IncomingApiHttpRequest request = new IncomingApiHttpRequest();
        request.processPath("GET /api/artist/b071f9fa-14b0-4217-8e97-eb41da73f598/ HTTP/1.1\n");
        ApiResponse response
                = controller.processRequest(Pattern.compile("^/api/artist/([a-zA-Z0-9-]*)/$"), request);
        verify(artistService).findArtist("b071f9fa-14b0-4217-8e97-eb41da73f598");
    }

    @Test
    public void processRequest_should_extract_artist_and_page_number_from_request_path()
            throws ParserConfigurationException, SAXException, IOException {
        IncomingApiHttpRequest request = new IncomingApiHttpRequest();
        request.processPath("GET /api/artist/b071f9fa-14b0-4217-8e97-eb41da73f598/top-tracks/1/ HTTP/1.1\n");
        ApiResponse response
                = controller.processRequest(Pattern.compile("^/api/artist/([a-zA-Z0-9-]*)/top-tracks/([0-9])/$"),
                request);
        verify(artistService).findArtistTopTracks("b071f9fa-14b0-4217-8e97-eb41da73f598", "1");
    }

}