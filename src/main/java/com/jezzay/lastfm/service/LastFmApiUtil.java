package com.jezzay.lastfm.service;

public final class LastFmApiUtil {
    //TODO: move this to a config file
    private static final String LAST_FM_API_BASE = "https://ws.audioscrobbler.com/2.0/?method=";
    private static final String LAST_FM_GEO_TOP_ARTIST_METHOD = "geo.gettopartists";
    private static final String LAST_FM_ARTIST_INFO_METHOD = "artist.getInfo";
    private static final String LAST_FM_ARTIST_TOP_TRACKS_METHOD = "artist.gettoptracks";

    public static String createGeoTopArtistUrl(String country, String pageNumber, String apiKey) {
        return LAST_FM_API_BASE + LAST_FM_GEO_TOP_ARTIST_METHOD
                + "&limit=5&country=" + country + "&page=" + pageNumber + "&api_key=" + apiKey;
    }

    public static String createArtistInfoUrl(String mbid, String apiKey) {
        return LAST_FM_API_BASE + LAST_FM_ARTIST_INFO_METHOD + "&mbid=" + mbid + "&api_key=" + apiKey;
    }

    public static String createArtistTopTracksUrl(String mbid, String pageNumber, String apiKey) {
        return LAST_FM_API_BASE + LAST_FM_ARTIST_TOP_TRACKS_METHOD + "&limit=5&mbid=" + mbid + "&page="
                + pageNumber + "&api_key=" + apiKey;
    }
}
