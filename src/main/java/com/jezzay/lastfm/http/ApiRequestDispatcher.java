package com.jezzay.lastfm.http;

import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;

/**
 * Dispatches incoming API requests to an appropriate handler based on the request getPath
 */
public interface ApiRequestDispatcher {

    ApiResponse dispatchRequest(IncomingApiHttpRequest request);
}
