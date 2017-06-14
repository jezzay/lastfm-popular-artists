package com.jezzay.lastfm.controller.impl;

import com.jezzay.lastfm.controller.ApiController;
import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.service.ArtistService;
import com.jezzay.lastfm.service.impl.ArtistServiceImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArtistController implements ApiController {

    private final ArtistService artistService;

    public ArtistController() {
        this.artistService = new ArtistServiceImpl();
    }

    ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public ApiResponse processRequest(Pattern pathPattern, IncomingApiHttpRequest httpRequest) {
        Matcher matcher = pathPattern.matcher(httpRequest.getPath());
        if (matcher.find()) {
            String mbid = matcher.group(1);
            try {
                Artist artist = artistService.findArtist(mbid);
                return ApiResponse.createSuccess(artist);
            } catch (SAXException | ParserConfigurationException | IOException e) {
                e.printStackTrace();
                return ApiResponse.createFailure("Unable to retrieve results for " + mbid);
            }
        }
        return ApiResponse.createFailure("Not able to extract mbid from request");
    }
}
