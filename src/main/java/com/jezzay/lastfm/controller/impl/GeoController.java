package com.jezzay.lastfm.controller.impl;

import com.jezzay.lastfm.controller.ApiController;
import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.service.GeoService;
import com.jezzay.lastfm.service.impl.GeoServiceImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeoController implements ApiController {

    private final GeoService topArtistService;

    public GeoController() {
        this.topArtistService = new GeoServiceImpl();
    }

    GeoController(GeoService topArtistService) {
        this.topArtistService = topArtistService;
    }

    @Override
    public ApiResponse processRequest(Pattern pathPattern, IncomingApiHttpRequest httpRequest) {
        Matcher matcher = pathPattern.matcher(httpRequest.getPath());
        if (matcher.find()) {
            String country = matcher.group(1);
            String page = matcher.group(2);
            try {
                List<Artist> artists = topArtistService.findTopArtistsFor(country, page);
                if (!artists.isEmpty()) {
                    return ApiResponse.createSuccess(artists);
                }
                return ApiResponse.createFailure("No results found for country " + country);
            } catch (IOException | ParserConfigurationException | SAXException e) {
                e.printStackTrace();
                return ApiResponse.createFailure("Unable to retrieve results for " + country);
            }
        }
        return ApiResponse.createFailure("Not able to extract country information from request");
    }
}
