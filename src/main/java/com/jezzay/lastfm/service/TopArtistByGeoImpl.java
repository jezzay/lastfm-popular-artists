package com.jezzay.lastfm.service;

import com.jezzay.lastfm.domain.Artist;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TopArtistByGeoImpl implements TopArtistByGeoService {

    @Override
    public List<Artist> findTopArtistsFor(String country, String page) throws IOException,
            ParserConfigurationException, SAXException {
        String apiKey = System.getenv("LAST_FM_API_KEY");
        List<Artist> results = new ArrayList<>();

        InputStream inputStream = connectToEndpoint(LastFmApiUtil.createGeoTopArtistUrl(country, page, apiKey));
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

    private Document parseXMLResponse(InputStream inputStream) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        return documentBuilder.parse(inputStream);
    }

    private InputStream connectToEndpoint(String url) throws IOException {
        URL endpoint = new URL(url);
        HttpsURLConnection urlConnection = (HttpsURLConnection) endpoint.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }

    private Artist processArtistNode(NodeList artistNodeList) {
        Artist artist = new Artist();
        for (int i = 0; i < artistNodeList.getLength(); i++) {
            Node node = artistNodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elm = (Element) node;
                String textContent = elm.getTextContent();
                switch (node.getNodeName()) {
                    case "name":
                        artist.setName(textContent);
                        break;
                    case "mbid":
                        artist.setMbid(textContent);
                        break;
                    case "image":
                        if (elm.hasAttribute("size") && elm.getAttribute("size").equals("medium")) {
                            artist.setImageURL(textContent);
                        }
                        break;
                }
            }
        }
        return artist;
    }
}
