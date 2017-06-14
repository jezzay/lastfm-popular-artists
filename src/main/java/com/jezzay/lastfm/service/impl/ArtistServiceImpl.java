package com.jezzay.lastfm.service.impl;

import static org.w3c.dom.Node.ELEMENT_NODE;

import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.domain.ArtistTrack;
import com.jezzay.lastfm.service.ArtistService;
import com.jezzay.lastfm.service.LastFmApiUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ArtistServiceImpl extends LastFmApiServiceBase implements ArtistService {
    @Override
    public Artist findArtist(String mbid) throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = connectToEndpoint(LastFmApiUtil.createArtistInfoUrl(mbid, apiKey()));
        Document document = parseXMLResponse(inputStream);
        Node artistNode = document.getElementsByTagName("artist").item(0);
        NodeList artistNodeChildNodes = artistNode.getChildNodes();
        return processArtistNode(artistNodeChildNodes);
    }

    @Override
    public List<ArtistTrack> findArtistTopTracks(String mbid, String pageNumber)
            throws IOException, ParserConfigurationException, SAXException {

        InputStream inputStream = connectToEndpoint(LastFmApiUtil.createArtistTopTracksUrl(mbid, pageNumber, apiKey()));
        Document document = parseXMLResponse(inputStream);
        Node topTracksNode = document.getElementsByTagName("toptracks").item(0);
        NodeList topTracksNodeChildNodes = topTracksNode.getChildNodes();
        return processTopTracksNodes(topTracksNodeChildNodes);
    }

    private List<ArtistTrack> processTopTracksNodes(NodeList topTracksNodes) {
        List<ArtistTrack> results = new ArrayList<>();
        for (int i = 0; i < topTracksNodes.getLength(); i++) {
            Node trackNode = topTracksNodes.item(i);
            if (trackNode.getNodeName().equals("track")) {
                if (trackNode.getNodeType() == ELEMENT_NODE) {
                    Element elm = (Element) trackNode;
                    results.add(processTrackNode(trackNode.getChildNodes(), elm.getAttribute("rank")));
                }
            }
        }
        return results;
    }

    private ArtistTrack processTrackNode(NodeList trackNode, String rank) {
        ArtistTrack artistTrack = new ArtistTrack();
        artistTrack.setRank(Integer.parseInt(rank));
        for (int i = 0; i < trackNode.getLength(); i++) {
            Node node = trackNode.item(i);
            if (node.getNodeType() == ELEMENT_NODE) {
                Element elm = (Element) node;
                String textContent = escapeString(elm.getTextContent());
                switch (node.getNodeName()) {
                    case "name":
                        artistTrack.setName(textContent);
                        break;
                    case "mbid":
                        artistTrack.setMbid(textContent);
                        break;
                    default:
                        break;
                }
            }
        }
        return artistTrack;
    }
}
