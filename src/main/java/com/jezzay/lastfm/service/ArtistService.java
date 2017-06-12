package com.jezzay.lastfm.service;

import com.jezzay.lastfm.domain.Artist;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface ArtistService {
    Artist findArtist(String mbid) throws IOException, ParserConfigurationException, SAXException;
}
