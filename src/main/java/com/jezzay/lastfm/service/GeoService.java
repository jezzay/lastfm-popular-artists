package com.jezzay.lastfm.service;

import com.jezzay.lastfm.domain.Artist;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface GeoService {
    List<Artist> findTopArtistsFor(String country, String page) throws IOException,
            ParserConfigurationException, SAXException;
}
