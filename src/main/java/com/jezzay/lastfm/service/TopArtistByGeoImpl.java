package com.jezzay.lastfm.service;

import com.jezzay.lastfm.domain.Artist;

import java.util.ArrayList;
import java.util.List;

public class TopArtistByGeoImpl implements TopArtistByGeoService {
    @Override
    public List<Artist> findTopArtistsFor(String country) {
        Artist artist = new Artist();
        artist.setName("Test");
        artist.setImageURL("http://blah");
        artist.setMbid("cc197bad-dc9c-440d-a5b5-d52ba2e14234");

        Artist artist2 = new Artist();
        artist2.setName("Test");
        artist2.setImageURL("http://blah");
        artist2.setMbid("cc197bad-dc9c-440d-a5b5-d52ba2e14234");


        List<Artist> results = new ArrayList<>();
        results.add(artist);
        results.add(artist2);
        return results;
    }
}
