package com.jezzay.lastfm.service;

import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.domain.ArtistTrack;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface ArtistService {
    Artist findArtist(String mbid) throws IOException, ParserConfigurationException, SAXException;
    List<ArtistTrack> findArtistTopTracks(String mbid, String pageNumber) throws IOException, ParserConfigurationException, SAXException;
}
