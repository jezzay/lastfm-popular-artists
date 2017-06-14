package com.jezzay.lastfm.service.impl;

import static org.w3c.dom.Node.ELEMENT_NODE;

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

abstract class LastFmApiServiceBase {

    String apiKey() {
        return System.getenv("LAST_FM_API_KEY");
    }

    InputStream connectToEndpoint(String url) throws IOException {
        URL endpoint = new URL(url);
        HttpsURLConnection urlConnection = (HttpsURLConnection) endpoint.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }

    Document parseXMLResponse(InputStream inputStream) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        return documentBuilder.parse(inputStream);
    }

    String escapeString(String data) {
        return data != null ? data.replaceAll("\"", "") : "";
    }

    Artist processArtistNode(NodeList artistNodeList) {
        Artist artist = new Artist();
        for (int i = 0; i < artistNodeList.getLength(); i++) {
            Node node = artistNodeList.item(i);
            if (node.getNodeType() == ELEMENT_NODE) {
                Element elm = (Element) node;
                String textContent = escapeString(elm.getTextContent());
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
                    default:
                        break;
                }
            }
        }
        return artist;
    }
}
