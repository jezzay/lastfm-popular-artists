package com.jezzay.lastfm.controller.impl;

import com.jezzay.lastfm.controller.ApiController;
import com.jezzay.lastfm.domain.ApiResponse;
import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.domain.ArtistTrack;
import com.jezzay.lastfm.domain.IncomingApiHttpRequest;
import com.jezzay.lastfm.service.ArtistService;
import com.jezzay.lastfm.service.impl.ArtistServiceImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
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
            if (matcher.groupCount() > 1) {
                String pageNumber = matcher.group(2);
                return findArtistTopTracks(mbid, pageNumber);
            }
            return findArtist(mbid);
        }
        return ApiResponse.createFailure("Not able to extract mbid from request");
    }

    private ApiResponse findArtistTopTracks(String mbid, String pageNumber) {
        try {
            List<ArtistTrack> topTracks = artistService.findArtistTopTracks(mbid, pageNumber);
            if (!topTracks.isEmpty()) {
                return ApiResponse.createSuccess(topTracks);
            }
            return ApiResponse.createFailure("No top track results found for " + mbid);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return ApiResponse.createFailure("Unable to retrieve top tracks for results for " + mbid);
        }
    }

    private ApiResponse findArtist(String mbid) {
        try {
            Artist artist = artistService.findArtist(mbid);
            return ApiResponse.createSuccess(artist);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
            return ApiResponse.createFailure("Unable to retrieve results for " + mbid);
        }
    }
}
