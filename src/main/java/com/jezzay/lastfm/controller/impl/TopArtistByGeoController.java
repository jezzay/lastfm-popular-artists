package com.jezzay.lastfm.controller.impl;

import com.jezzay.lastfm.controller.ApiController;
import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.service.TopArtistByGeoImpl;
import com.jezzay.lastfm.service.TopArtistByGeoService;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopArtistByGeoController implements ApiController {

    private final TopArtistByGeoService topArtistService;

    public TopArtistByGeoController() {
        this.topArtistService = new TopArtistByGeoImpl();
    }

    TopArtistByGeoController(TopArtistByGeoService topArtistService) {
        this.topArtistService = topArtistService;
    }

    @Override
    public ApiResponse processRequest(Pattern pathPattern, IncomingApiHttpRequest httpRequest) {
        Matcher matcher = pathPattern.matcher(httpRequest.getPath());
        if (matcher.find()) {
            String country = matcher.group(1);
            List<Artist> artists = topArtistService.findTopArtistsFor(country);
            Optional<String> results = artists.stream().map(Artist::toString).reduce((s, acc) -> acc + "," + s);
            return results.map(s -> ApiResponse.createSuccess("{\"data\":[ " + s + "]}"))
                    .orElseGet(() -> ApiResponse.createFailure("No results found for " + country));
        }
        return ApiResponse.createFailure("Not able to extract country information from request");
    }
}
