package com.jezzay.lastfm;


import com.jezzay.lastfm.http.LastFmAPIServer;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        LastFmAPIServer lastFmAPIServer = new LastFmAPIServer(8080);
        lastFmAPIServer.acceptConnections();
    }
}

