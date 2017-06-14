package com.jezzay.lastfm.service;

public final class LastFmApiUtil {
    private static final String LAST_FM_API_BASE_DEFAULT = "https://ws.audioscrobbler.com/2.0/?method=";
    private static final String LAST_FM_GEO_TOP_ARTIST_METHOD_DEFAULT = "geo.gettopartists";
    private static final String LAST_FM_ARTIST_INFO_METHOD_DEFAULT = "artist.getInfo";
    private static final String LAST_FM_ARTIST_TOP_TRACKS_METHOD_DEFAULT = "artist.gettoptracks";

    public static String createGeoTopArtistUrl(String country, String pageNumber, String apiKey) {
        return apiBase() + geoTopArtistMethod()
                + "&limit=5&country=" + country + "&page=" + pageNumber + "&api_key=" + apiKey;
    }

    public static String createArtistInfoUrl(String mbid, String apiKey) {
        return apiBase() + artistInfoMethod() + "&mbid=" + mbid + "&api_key=" + apiKey;
    }

    public static String createArtistTopTracksUrl(String mbid, String pageNumber, String apiKey) {
        return apiBase() + LAST_FM_ARTIST_TOP_TRACKS_METHOD_DEFAULT + "&limit=5&mbid=" + mbid + "&page="
                + pageNumber + "&api_key=" + apiKey;
    }

    private static String apiBase() {
        return envVariable("LAST_FM_API_REMOTE_API_BASE", LAST_FM_API_BASE_DEFAULT);
    }

    private static String geoTopArtistMethod() {
        return envVariable("LAST_FM_API_REMOTE_TOP_ARTIST_METHOD", LAST_FM_GEO_TOP_ARTIST_METHOD_DEFAULT);
    }

    private static String artistInfoMethod() {
        return envVariable("LAST_FM_API_REMOTE_ARTIST_INFO_METHOD", LAST_FM_ARTIST_INFO_METHOD_DEFAULT);
    }

    private static String artistTopTracksMethod() {
        return envVariable("LAST_FM_API_REMOTE_ARTIST_TOP_TRACKS_METHOD", LAST_FM_ARTIST_TOP_TRACKS_METHOD_DEFAULT);
    }

    private static String envVariable(String envName, String defaultValue) {
        String envVar = System.getenv(envName);
        return envVar != null ? envName : defaultValue;
    }

}
