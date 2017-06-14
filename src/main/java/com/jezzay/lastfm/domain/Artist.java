package com.jezzay.lastfm.domain;

public class Artist {
    private String name;
    private String mbid;
    private String imageURL;

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return String.format("{\"name\":\"%s\",\"mbid\":\"%s\",\"imageURL\":\"%s\"}", name, mbid, imageURL);
    }
}
