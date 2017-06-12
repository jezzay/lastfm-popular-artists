package com.jezzay.lastfm.service;

import com.jezzay.lastfm.domain.Artist;

import java.util.List;

public interface TopArtistByGeoService {
    List<Artist> findTopArtistsFor(String country);
}
