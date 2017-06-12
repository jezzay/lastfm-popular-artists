package com.jezzay.lastfm.service;

public final class LastFmApiUtil {
    //TODO: move this to a config file
    private static final String LAST_FM_API_BASE = "https://ws.audioscrobbler.com/2.0/?method=";
    private static final String LAST_FM_GEO_TOP_ARTIST_METHOD = "geo.gettopartists";

    public static String createGeoTopArtistUrl(String country, String pageNumber, String apiKey) {
        return LAST_FM_API_BASE + LAST_FM_GEO_TOP_ARTIST_METHOD
                + "&limit=5&country=" + country + "&page=" + pageNumber + "&api_key=" + apiKey;
    }
}
