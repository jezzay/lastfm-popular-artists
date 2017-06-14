package com.jezzay.lastfm.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.service.GeoService;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class GeoServiceImplIntegrationTest {

    private GeoService geoService;

    @Before
    public void setUp() throws Exception {
        this.geoService = new GeoServiceImpl();
    }

    @Test
    public void findTopArtistsFor_should_return_results_for_country()
            throws ParserConfigurationException, SAXException, IOException {
        List<Artist> results = geoService.findTopArtistsFor("Australia", "1");
        assertEquals(5, results.size());
    }

    @Test
    public void findTopArtistsFor_should_return_different_results_for_each_page()
            throws ParserConfigurationException, SAXException, IOException {
        List<Artist> pageOneResults = geoService.findTopArtistsFor("Australia", "1");
        List<Artist> pageTwoResults = geoService.findTopArtistsFor("Australia", "2");
        assertEquals(5, pageOneResults.size());
        assertEquals(5, pageTwoResults.size());
        assertNotEquals(pageOneResults, pageTwoResults);
    }
}