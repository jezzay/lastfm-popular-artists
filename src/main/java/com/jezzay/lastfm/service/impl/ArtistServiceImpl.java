package com.jezzay.lastfm.service.impl;

import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.service.ArtistService;
import com.jezzay.lastfm.service.LastFmApiUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class ArtistServiceImpl extends LastFmApiServiceBase implements ArtistService {
    @Override
    public Artist findArtist(String mbid) throws IOException, ParserConfigurationException, SAXException {
        String apiKey = System.getenv("LAST_FM_API_KEY");
        InputStream inputStream = connectToEndpoint(LastFmApiUtil.createArtistInfoUrl(mbid, apiKey));
        Document document = parseXMLResponse(inputStream);
        Node artistNode = document.getElementsByTagName("artist").item(0);
        NodeList artistNodeChildNodes = artistNode.getChildNodes();
        return processArtistNode(artistNodeChildNodes);
    }
}
