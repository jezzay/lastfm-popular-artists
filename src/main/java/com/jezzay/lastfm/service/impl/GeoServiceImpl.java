package com.jezzay.lastfm.service.impl;

import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.service.GeoService;
import com.jezzay.lastfm.service.LastFmApiUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeoServiceImpl extends LastFmApiServiceBase implements GeoService {

    @Override
    public List<Artist> findTopArtistsFor(String country, String page) throws IOException,
            ParserConfigurationException, SAXException {
        List<Artist> results = new ArrayList<>();

        InputStream inputStream = connectToEndpoint(LastFmApiUtil.createGeoTopArtistUrl(country, page, apiKey()));
        Document document = parseXMLResponse(inputStream);

        Node topArtists = document.getElementsByTagName("topartists").item(0);
        NodeList topArtistsChildNodes = topArtists.getChildNodes();
        for (int i = 0; i < topArtistsChildNodes.getLength(); i++) {
            Node artistNode = topArtistsChildNodes.item(i);
            if (artistNode.getNodeName().equals("artist")) {
                NodeList artistElementsList = artistNode.getChildNodes();
                results.add(processArtistNode(artistElementsList));
            }
        }
        return results;
    }
}
