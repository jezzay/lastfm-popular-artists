package com.jezzay.lastfm.domain;

public class ArtistTrack {
    private String name;
    private String mbid;
    private int rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return String.format("{\"name\":\"%s\",\"mbid\":\"%s\",\"rank\":\"%s\"}", name, mbid, rank);
    }
}
