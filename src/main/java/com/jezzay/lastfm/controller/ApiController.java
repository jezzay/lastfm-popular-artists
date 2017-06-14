package com.jezzay.lastfm.controller;

import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;

import java.util.regex.Pattern;

public interface ApiController {
    ApiResponse processRequest(Pattern pathPattern, IncomingApiHttpRequest httpRequest);
}
