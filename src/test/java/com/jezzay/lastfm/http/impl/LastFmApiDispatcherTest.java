package com.jezzay.lastfm.http.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.jezzay.lastfm.controller.ApiController;
import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RunWith(MockitoJUnitRunner.class)
public class LastFmApiDispatcherTest {

    private LastFmApiDispatcher apiDispatcher;
    private Map<Pattern, ApiController> apiMapping;
    @Mock
    private ApiController controller;

    @Before
    public void setUp() throws Exception {
        apiDispatcher = new LastFmApiDispatcher();
        apiMapping = new HashMap<>();
    }

    @Test
    public void dispatchRequest_should_dispatch_request_to_controller_when_path_matches() {
        IncomingApiHttpRequest incomingApiHttpRequest = new IncomingApiHttpRequest();
        incomingApiHttpRequest.processPath("GET /api/geo/top-artist/Australia/ HTTP/1.1\n");
        Pattern apiPattern = Pattern.compile("^/api/geo/top-artist/([a-zA-Z]*)/$");
        addApiMapping(apiPattern);
        apiDispatcher.dispatchRequest(incomingApiHttpRequest);
        verify(controller).processRequest(apiPattern, incomingApiHttpRequest);
    }

    @Test
    public void dispatchRequest_should_return_api_error_when_path_does_not_match() {
        IncomingApiHttpRequest incomingApiHttpRequest = new IncomingApiHttpRequest();
        incomingApiHttpRequest.processPath("GET /api/not-found HTTP/1.1\n");
        Pattern apiPattern = Pattern.compile("^/api/geo/top-artist/([a-zA-Z]*)/$");
        addApiMapping(apiPattern);
        ApiResponse apiResponse = apiDispatcher.dispatchRequest(incomingApiHttpRequest);
        assertEquals(false, apiResponse.isSuccessful());

    }

    private void addApiMapping(Pattern... paths) {
        for (Pattern path : paths) {
            apiMapping.put(path, controller);
        }
        apiDispatcher.setApiMapping(apiMapping);
    }

}