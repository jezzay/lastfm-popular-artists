package com.jezzay.lastfm.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.jezzay.lastfm.domain.Artist;
import com.jezzay.lastfm.domain.ArtistTrack;
import com.jezzay.lastfm.service.ArtistService;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class ArtistServiceImplIntegrationTest {

    private ArtistService artistService;

    @Before
    public void setUp() throws Exception {
        this.artistService = new ArtistServiceImpl();
    }

    @Test
    public void findArtist_should_find_artist_based_on_mbid()
            throws ParserConfigurationException, SAXException, IOException {
        Artist artist = this.artistService.findArtist("b071f9fa-14b0-4217-8e97-eb41da73f598");
        assertNotNull(artist);
        assertEquals("The Rolling Stones", artist.getName());
        assertEquals("b071f9fa-14b0-4217-8e97-eb41da73f598", artist.getMbid());
    }

    @Test
    public void findArtist_should_throw_exception_when_unable_to_find_artist() {
        try {
            Artist artist = this.artistService.findArtist("b071f9fa");
            fail("Expected to be unable to find result");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            // :)
        }
    }

    @Test
    public void findArtistTopTracks_should_find_top_tracks_for_artist()
            throws ParserConfigurationException, SAXException, IOException {
        List<ArtistTrack> artistTopTracks =
                artistService.findArtistTopTracks("b071f9fa-14b0-4217-8e97-eb41da73f598", "1");
        assertEquals(5, artistTopTracks.size());
    }

    @Test
    public void findArtistTopTracks_should_return_different_results_for_each_page()
            throws ParserConfigurationException, SAXException, IOException {
        List<ArtistTrack> artistTopTracksPageOne =
                artistService.findArtistTopTracks("b071f9fa-14b0-4217-8e97-eb41da73f598", "1");
        List<ArtistTrack> artistTopTracksPageTwo =
                artistService.findArtistTopTracks("b071f9fa-14b0-4217-8e97-eb41da73f598", "2");
        assertNotEquals(artistTopTracksPageOne, artistTopTracksPageTwo);


    }
}